package com.imooc.security.browser;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationfailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }*/

    /*
        自定义登录界面
        存在的问题：
            1.如果发送REST服务，应该返回状态码，json字符串
            2.如果是html请求，返回登陆界面
            3.登陆界面的制定
     */
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/imooc-signIn.html")
                //使用UsernamePasswordAuthenticationFilter处理请求
                .loginProcessingUrl("//authentication/form")
                //授权
                .and()
                .authorizeRequests()
                //当访问这个Url的时候，不需要身份认证就可以访问
                .antMatchers("/imooc-signIn.html").permitAll()
                // 所有的请求都要身份认证
                .anyRequest()
                .authenticated()
                //取消跨站请求防护的功能
                .and()
                .csrf().disable();

    }*/

     @Override
    protected void configure(HttpSecurity http) throws Exception {

         ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
         validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationfailureHandler);
         validateCodeFilter.setSecurityProperties(securityProperties);
         validateCodeFilter.afterPropertiesSet();

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationfailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage(),"/code/image").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
