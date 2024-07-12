package cn.gojava.app.common.utils.excel.config;

import cn.gojava.app.common.utils.cache.CacheUtils;
import cn.gojava.app.entity.SysDictData;
import cn.gojava.app.service.SysDictDataService;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * 字典工具类
 */
@Slf4j
public class DictFrameworkUtils {

    private static SysDictDataService sysDictDataService;

    private static final SysDictData DICT_DATA_NULL = new SysDictData();

    /**
     * 针对 {@link #getDictDataLabel(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, SysDictData> GET_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, SysDictData>() {

                @Override
                public @NotNull SysDictData load(@NotNull KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(sysDictDataService.getDictData(key.getKey(), key.getValue()), DICT_DATA_NULL);
                }

            });

    /**
     * 针对 {@link #parseDictDataValue(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, SysDictData> PARSE_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, SysDictData>() {

                @Override
                public @NotNull SysDictData load(@NotNull KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(sysDictDataService.parseDictData(key.getKey(), key.getValue()), DICT_DATA_NULL);
                }

            });

    public static void init(SysDictDataService sysDictDataService) {
        DictFrameworkUtils.sysDictDataService = sysDictDataService;
        log.info("[init][初始化 DictFrameworkUtils 成功]");
    }

    @SneakyThrows
    public static String getDictDataLabel(String dictType, Integer value) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, String.valueOf(value))).getDictLabel();
    }

    /**
     * 获取 {@link SysDictData#getDictLabel()} () 字典键值}
     *
     * @param dictType 字典类型
     * @param value    字典键值
     * @return 字典数据
     */
    @SneakyThrows
    public static String getDictDataLabel(String dictType, String value) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, value)).getDictLabel();
    }

    /**
     * 获取 {@link SysDictData#getDictValue() 字典键值}
     *
     * @param dictType 字典类型
     * @param label    字典标签
     * @return 字典数据
     */
    @SneakyThrows
    public static String parseDictDataValue(String dictType, String label) {
        return PARSE_DICT_DATA_CACHE.get(new KeyValue<>(dictType, label)).getDictValue();
    }

}
