package cn.gojava.app.common.utils.csv;

import cn.gojava.app.common.exception.ServiceException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public abstract class BaseCsvReader {

    private static final CsvMapper csvMapper = new CsvMapper();

    static {
        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY); // 如果希望按数组方式解析
        csvMapper.enable(CsvGenerator.Feature.STRICT_CHECK_FOR_QUOTING); // 可选，启用严格的引号检查
    }

    public abstract CsvSchema getCsvSchema();

    public <T> T readValue(String csvString, Class<T> clazz) {
        ObjectReader objectReader = csvMapper.readerFor(clazz).with(getCsvSchema());

        try (MappingIterator<T> iterator = objectReader.readValues(csvString)) {
            if (iterator.hasNext()) {
                return iterator.next();
            }
        } catch (IOException e) {
            log.error("服务器异常，数据解析失败", e);
        }
        throw new ServiceException("服务器异常，数据解析失败");
    }


}
