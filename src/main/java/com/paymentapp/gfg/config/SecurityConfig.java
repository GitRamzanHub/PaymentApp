package com.paymentapp.gfg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http
//                .authorizeRequests()
//                .antMatchers("/login/oauth2/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();
//    }
//@Override
//protected void configure(HttpSecurity http) throws Exception{
//    http
//            .authorizeRequests()
//            .antMatchers("/login","/dashboard")// "/oauth/**"
//            .permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .oauth2Login()
//            .loginPage("/login") // Set the custom login page URL
//            .and()
//            .oauth2Login()
//            .defaultSuccessUrl("/dashboard") // set the URL to redirect after login
//            .and()
//            .logout()
//            .logoutUrl("/logout") // Configure the logout URL
//            .logoutSuccessUrl("/login?logout") // Specify the URL to redirect to after successful logout
//            .invalidateHttpSession(true)
//            .clearAuthentication(true)
//            .deleteCookies("JSESSIONID");
//    }
//
//}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/dashboard")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login") // Set the custom login page URL
                .defaultSuccessUrl("/dashboard") // Set the URL to redirect after login
                .and()
                .logout()
                .logoutUrl("/logout") // Configure the logout URL
                .logoutSuccessUrl("/login?logout") // Specify the URL to redirect to after successful logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");
    }

}
