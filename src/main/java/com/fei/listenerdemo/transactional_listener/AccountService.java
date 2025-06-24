package com.fei.listenerdemo.transactional_listener;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 账户服务，演示事务提交和回滚时事件监听效果
 */
@Service
public class AccountService {

    private final JdbcTemplate jdbcTemplate;
    private final ApplicationEventPublisher publisher;

    public AccountService(JdbcTemplate jdbcTemplate, ApplicationEventPublisher publisher) {
        this.jdbcTemplate = jdbcTemplate;
        this.publisher = publisher;
    }

    /**
     * 转账（正常提交）
     */
    @Transactional
    public void transfer(String from, String to, double amount) {
        jdbcTemplate.update("UPDATE transactional_listener_demo_account SET balance = balance - ? WHERE name = ?", amount, from);
        jdbcTemplate.update("UPDATE transactional_listener_demo_account SET balance = balance + ? WHERE name = ?", amount, to);
        publisher.publishEvent(new TransactionalEventDemo.AccountChangeEvent(this, "转账成功: " + from + " -> " + to + ", 金额: " + amount));
    }

    /**
     * 转账（强制回滚）
     */
    @Transactional
    public void transferWithRollback(String from, String to, double amount) {
        jdbcTemplate.update("UPDATE transactional_listener_demo_account SET balance = balance - ? WHERE name = ?", amount, from);
        jdbcTemplate.update("UPDATE transactional_listener_demo_account SET balance = balance + ? WHERE name = ?", amount, to);
        publisher.publishEvent(new TransactionalEventDemo.AccountChangeEvent(this, "转账失败: " + from + " -> " + to + ", 金额: " + amount));
        // 强制回滚
        throw new RuntimeException("模拟异常，事务回滚");
    }
} 