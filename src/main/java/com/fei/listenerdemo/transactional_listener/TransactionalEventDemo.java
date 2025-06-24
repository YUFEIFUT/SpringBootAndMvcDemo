package com.fei.listenerdemo.transactional_listener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

/**
 * 【Demo说明】
 * <p>
 * 本Demo用于演示Spring事务事件监听（@TransactionalEventListener）在真实业务场景下的四种phase属性（BEFORE_COMMIT、AFTER_COMMIT、AFTER_ROLLBACK、AFTER_COMPLETION）效果。
 * <p>
 * 【背景与目的】
 * - 事务事件监听器可用于在事务的不同阶段（提交前、提交后、回滚后、完成后）执行自定义逻辑，常用于解耦业务与事务处理、异步通知、审计等场景。
 * - 本Demo通过账户转账业务，结合MySQL数据库，真实演示四种监听时机的区别和效果。
 * <p>
 * 【表结构说明】
 * - 表名：transactional_listener_demo_account（为避免与实际业务表冲突，采用独特命名）
 * - 字段：id, name, balance
 * - 初始化脚本见 resources/transactional_listener_demo/schema.sql 和 data.sql
 * <p>
 * 【如何使用】
 * 1. 启动Spring Boot项目，确保数据库连接正常，表和数据会自动初始化。
 * 2. 通过浏览器或Postman调用如下接口：
 *    - 正常转账（事务提交）：
 *      GET <a href="http://localhost:8888/account/transfer?from=Alice&to=Bob&amount=100">...</a>
 *    - 转账并回滚（事务回滚）：
 *      GET <a href="http://localhost:8888/account/transferRollback?from=Alice&to=Bob&amount=100">...</a>
 * 3. 观察控制台输出，四种phase的监听器会在不同事务阶段打印日志：
 *    - BEFORE_COMMIT：事务提交前
 *    - AFTER_COMMIT：事务提交后（仅提交时触发）
 *    - AFTER_ROLLBACK：事务回滚后
 *    - AFTER_COMPLETION：事务完成后（无论提交还是回滚都会触发）
 * <p>
 * 【注意事项】
 * - data.sql 每次初始化会清空 transactional_listener_demo_account 表，避免影响实际业务表。
 * - 监听器逻辑可根据实际业务扩展，如异步通知、日志、审计等。
 * <p>
 * 【适用场景举例】
 * - 订单支付成功后异步通知
 * - 事务回滚时补偿处理
 * - 事务完成后统一清理资源
 * <p>
 * 【BEFORE_COMMIT 事务传播行为补充说明】
 * <p>
 * - phase = TransactionPhase.BEFORE_COMMIT 的监听器会在主业务事务即将提交前被调用。
 * - 默认情况下（不加 @Transactional 或 @Transactional(propagation = REQUIRED)），监听器和业务方法共用同一个事务，监听器抛异常会导致主事务回滚。
 * - 如果监听器加 @Transactional(propagation = REQUIRES_NEW)，则会挂起主事务，监听器操作在新事务中执行，互不影响。
 * - 其他 phase（AFTER_COMMIT、AFTER_ROLLBACK、AFTER_COMPLETION）都是在主事务完成后触发，和主事务隔离，无传播问题。
 * <p>
 * 【建议】
 * - BEFORE_COMMIT 适合做强一致性要求的操作（如审计、日志、校验等），需注意异常会影响主事务。
 * - 如需隔离，可用 REQUIRES_NEW 或选择 AFTER_COMMIT 等 phase。
 */
public class TransactionalEventDemo {
    /**
     * 事件定义
     */
    @Getter
    public static class AccountChangeEvent extends ApplicationEvent {
        private final String msg;
        public AccountChangeEvent(Object source, String msg) {
            super(source);
            this.msg = msg;
        }
    }

    /**
     * 监听器组件
     */
    @Component
    public static class AccountChangeEventListener {
        @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
        public void beforeCommit(AccountChangeEvent event) {
            System.out.println("【BEFORE_COMMIT】事务提交前收到事件: " + event.getMsg());
        }
        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
        public void afterCommit(AccountChangeEvent event) {
            System.out.println("【AFTER_COMMIT】事务提交后收到事件: " + event.getMsg());
        }
        @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
        public void afterRollback(AccountChangeEvent event) {
            System.out.println("【AFTER_ROLLBACK】事务回滚后收到事件: " + event.getMsg());
        }
        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
        public void afterCompletion(AccountChangeEvent event) {
            System.out.println("【AFTER_COMPLETION】事务完成后收到事件: " + event.getMsg());
        }
    }
} 