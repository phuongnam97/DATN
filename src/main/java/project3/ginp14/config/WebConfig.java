package project3.ginp14.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import project3.ginp14.service.UserServiceImpl;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        //http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/static/admin/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/home", "/users/register", "/logout").permitAll()
                // cho phép all admin
                .antMatchers("/admin/**").permitAll()
                // cho phép all user
                .antMatchers("/users/**").permitAll()
                // cho phép restaurant
                .antMatchers("/restaurant","/restaurant/**").permitAll()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/").permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/login-successful")
                .failureUrl("/");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
        return memory;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

//    public AuthenticationSuccessHandler loginSuccessHandler() {
//        return (request, response, exception) -> response.sendRedirect("/");
//    }
//
//    public AuthenticationFailureHandler loginFailureHandler() {
//        return (request, response, exception) -> response.sendRedirect("/users/login?error=true");
//    }
}
