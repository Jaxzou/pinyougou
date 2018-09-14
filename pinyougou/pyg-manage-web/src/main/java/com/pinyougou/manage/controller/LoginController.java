package com.pinyougou.manage.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-13 20:53
 * @desc 登录控制器
 **/
@RestController
@RequestMapping("login")
public class LoginController {

    /**
     * 在security 认证信息中取出登录人信息
     * @return 当前登录人
     */
    @GetMapping("getUsername")
    public Map<String,Object> getUsername(){
        Map<String,Object> map = new HashMap<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username",username);

        return map;
    }
}
