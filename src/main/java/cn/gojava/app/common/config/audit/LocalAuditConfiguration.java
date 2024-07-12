package cn.gojava.app.common.config.audit;

import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 本地SQL分析审计日志配置
 */
@Profile({"local", "dev", "pro"})
@Configuration
public class LocalAuditConfiguration {

    private static final Logger logger = LoggerFactory.getLogger("local-audit");

    public LocalAuditConfiguration() {
        // 开启审计功能
        AuditManager.setAuditEnable(true);
        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.debug("[{}ms] | {}", auditMessage.getElapsedTime(), auditMessage.getFullSql())
        );
    }
}