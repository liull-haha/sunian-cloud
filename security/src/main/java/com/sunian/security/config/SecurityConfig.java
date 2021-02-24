package com.sunian.security.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liull
 * @Description:
 * @Date: 2020/12/4 22:14
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserService customUserService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 校验码验证
     *
     * @return
     */
    @Bean
    MyAuthenticationProvider myAuthenticationProvider() {
        MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
        myAuthenticationProvider.setUserDetailsService(customUserService);
        myAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return myAuthenticationProvider;
    }

    /**
     * 添加自定义校验逻辑
     *
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        ProviderManager providerManager = new ProviderManager(Arrays.asList(myAuthenticationProvider()));
        return providerManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService);
    }

    /**
     * 角色继承  admin角色权限大于user角色权限，则user角色可以访问的资源，admin也可以访问
     *
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//                .usernameParameter("username")
//                .passwordParameter("password")
//                 前后端不分离使用
//                自定义登录成功URL（重定向） 从哪个页面来到登录页面，登录成功后回到哪个页面
//                .defaultSuccessUrl("/hello/helloMethod")
//                 自定义登录成功URL（转发）
//                .successForwardUrl("/success")
//                .failureForwardUrl("/failure")
//                .failureUrl("/failure")

        http.authorizeRequests()
                //配置权限拦截规则：admin角色只能访问/admin/**资源，user角色只能访问/user/**资源
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                //放行验证码URL
                .antMatchers("/hello/vc.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //自定义登录页面
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                //前后端分离使用
                .successHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JSONObject.toJSONString(authentication.getPrincipal()));
                    writer.flush();
                    writer.close();
                })
                .failureHandler((req, resp, exception) -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("respCode", "8888");
                    String message = exception.getMessage();
                    if (exception instanceof BadCredentialsException) {
                        message = "用户名或密码错误";
                    } else if (exception instanceof LockedException) {
                        message = "账户被锁定";
                    } else if (exception instanceof CredentialsExpiredException) {
                        message = "密码过期";
                    } else if (exception instanceof AccountExpiredException) {
                        message = "账户过期";
                    } else if (exception instanceof DisabledException) {
                        message = "账户被禁用";
                    }
                    resultMap.put("respDesc", message);
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JSONObject.toJSONString(resultMap));
                    writer.flush();
                    writer.close();
                })
                .permitAll()
                .and()
                .logout()
                //自定义退出URL
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutSuccessHandler((req, resp, authentication) -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("respCode", "0000");
                    resultMap.put("respDesc", "注销成功");
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JSONObject.toJSONString(resultMap));
                    writer.flush();
                    writer.close();
                })
                .and()
                .csrf().disable()
                .exceptionHandling()
                //没有认证授权处理异常
                .authenticationEntryPoint((req, resp, authException) -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("respCode", "0001");
                    resultMap.put("respDesc", "尚未登录，请登陆");
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JSONObject.toJSONString(resultMap));
                    writer.flush();
                    writer.close();
                });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //拦截忽略掉URL地址，首先忽略静态文件
        web.ignoring()
                .antMatchers("/js/**", "/css/**", "/images/**");
    }
}
