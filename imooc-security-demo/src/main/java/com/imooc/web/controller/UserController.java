package com.imooc.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.exception.UserNotExistException;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors, @PathVariable("id") String id){
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error ->
                    System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());
        System.out.println(user.getBirthday());
        return user;
    }

    // @Valid使校验注解生效
    @PostMapping
    public User create(@Valid @RequestBody User user){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());
        System.out.println(user.getBirthday());
        return user;
    }

    @JsonView(User.UserSimpleView.class)
    /*
    @GetMapping是@RequestMapping的增加版
     */
    //@RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping
    public List<User> query(UserQueryCondition condition/*@RequestParam String username*/){
//        System.out.println(username);
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    // @PathVariable将变量id值添加到方法参数中
    //@RequestMapping(value="/user/{id}",method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    // 正则表达式
    //@RequestMapping(value="/user/{id:\\d+}",method = RequestMethod.GET)
    @GetMapping("/{id:\\d+}")
    public User getInfo(@PathVariable String id){
        //throw new RuntimeException("user not exist");
        //throw new UserNotExistException(id);
        System.out.println("进去getInfo服务");
        User user = new User();
        user.setUsername("tom");
        return user;
    }
}
