package cn.gojava.app.common.utils.excel.config;


import cn.gojava.app.service.SysDictDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictAutoConfig {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(SysDictDataService sysDictDataService) {
        DictFrameworkUtils.init(sysDictDataService);
        return new DictFrameworkUtils();
    }

}
