package cn.gojava.app.common.utils.csv;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class CsvTestReader extends BaseCsvReader {
    @Override
    public CsvSchema getCsvSchema() {
        return Test.getCsvSchema();
    }

    @Data
    public static class Test {
        private Long id;
        private String name;
        private String age;
        public static CsvSchema getCsvSchema() {
            return CsvSchema.builder()
                    //.setColumnSeparator('|')
                    .addColumn("id")
                    .addColumn("name")
                    .addColumn("age")
                    .build();
        }
    }

}