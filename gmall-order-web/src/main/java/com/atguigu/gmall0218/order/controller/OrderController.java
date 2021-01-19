package com.atguigu.gmall0218.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0218.bean.UserAddress;
import com.atguigu.gmall0218.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
 @Reference
    private UserService userService;

   /* @RequestMapping("trade")
    public String trade(){
        return "index";
    }
*/
    @RequestMapping("trade")
    public List<UserAddress> trade(String userId){
        return userService.getUserAdressList(userId);
    }
}
