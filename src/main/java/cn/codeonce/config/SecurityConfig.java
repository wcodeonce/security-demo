package cn.codeonce.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Name: SecurityConfig
 * @Description: TODO
 * @Author: codeonce
 * @Date: 2022/5/26
 */

// Security 拦截器
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 首页所有人可以访问，功能页只有对应有权限的人才能访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasAnyRole("vip1")
                .antMatchers("/level2/**").hasAnyRole("vip2")
                .antMatchers("/level3/**").hasAnyRole("vip3");

        // 没有权限默认跳转到登录页面，需要开启登录的页面
        // formLogin 开启登录页面
        // loginPage 定制登录页
        // loginProcessingUrl 登录处理链接
        http.formLogin().loginPage("/toLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login");

        // 防止网上攻击: get 不安全 / post 安全
        http.csrf().disable();

        // 注销,开启注销功能 注销后跳转页面可以使用logoutUrl
        http.logout().logoutSuccessUrl("/").deleteCookies();

        // 开启记住我功能 cookie 默认保存两周
        http.rememberMe().rememberMeParameter("remember");

    }


    // 认证
    // 密码编码：PasswordEncoder
    // 在 SpringSecurity 5.0+ 新增了新的加密方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 通常情况下这些数据应该从数据库中读取
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2", "vip3")
                .and()
                .withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123123")).roles("vip1", "vip2")
                .and()
                .withUser("lisi").password(new BCryptPasswordEncoder().encode("123123")).roles("vip1");
    }
}
