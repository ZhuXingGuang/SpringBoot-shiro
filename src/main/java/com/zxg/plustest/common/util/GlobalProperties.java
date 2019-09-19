package com.zxg.plustest.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = GlobalProperties.SMS_PREFIX)
public class GlobalProperties {
    /**
     * 魔法数字的处理方式
     *
     * 可以交给静态块处理，但缺点是静态块会被编译
     *
     * 交给属性文件管理相对来说是一个不错的方法
     *
     * eg:zxg.errorCount=指定内容；
     *
     * 使用时直接在类中@Autowired注入GlobalProperties
     *
     * GlobalProperties.getErrorCount即可获得属性文件中对应的值
     *
     */
    public static final String SMS_PREFIX = "zxg";
    private Integer errorCount;

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
