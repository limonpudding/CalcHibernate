package testconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;

import static app.utils.PageNamesConstants.LOGIN_PAGE;


@Configuration
@ComponentScan(basePackages = "app.security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER and " +
                "ROLE_ADMIN > ROLE_MATH and " +
                "ROLE_MATH > ROLE_SUM_SUB and " +
                "ROLE_SUM_SUB > ROLE_USER");
        return roleHierarchy;
    }


    @Bean
    public UserDetailsService userDetailsService(){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        UserDetails userDetails = new User("admin", "$2a$10$5a6vv3yJZuAbpUSU04vAce2d6MACeDHJeDspyulKzbR2.tAu5W2Tm", Collections.singletonList(authority));
        return new InMemoryUserDetailsManager(Collections.singletonList(userDetails));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN_PAGE).permitAll().and()
                .formLogin().defaultSuccessUrl("/", false)
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .loginProcessingUrl("/perform_login")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll().and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }


}
