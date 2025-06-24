package com.fei.controller;

import com.fei.listenerdemo.transactional_listener.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户控制器，提供接口测试事务事件监听
 */
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 正常转账（事务提交）
     */
    @GetMapping("/account/transfer")
    public String transfer(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        accountService.transfer(from, to, amount);
        return "转账成功";
    }

    /**
     * 转账并回滚（事务回滚）
     */
    @GetMapping("/account/transferRollback")
    public String transferRollback(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        try {
            accountService.transferWithRollback(from, to, amount);
        } catch (Exception e) {
            return "转账失败，已回滚: " + e.getMessage();
        }
        return "不可能到这里";
    }
} 