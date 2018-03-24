package io.vscale.uniservice.security.config;

import io.vscale.uniservice.security.rest.handlers.RESTAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.sql.DataSource;

/**
 * 13.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@ComponentScan("io.vscale.uniservice")
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MultipleSecurityConfig {

    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean("myPasswordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(this.dataSource);
        return jdbcTokenRepository;
    }

    @Configuration
    @Order(1)
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter{

        private final UserDetailsService userDetailsService;
        private final AuthenticationProvider authenticationProvider;

        @Autowired
        public WebSecurityConfig(UserDetailsService userDetailsService, AuthenticationProvider authenticationProvider) {
            this.userDetailsService = userDetailsService;
            this.authenticationProvider = authenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception{

            http.authorizeRequests()
                  .antMatchers("/admin/**").hasAuthority("ADMIN")
                  .antMatchers("/student/**").hasAuthority("STUDENT")
                  .antMatchers("/cooperator/**").hasAuthority("COOPERATOR")
                  .antMatchers("/events/**").permitAll()
                  .antMatchers("/css/**").permitAll()
                  .antMatchers("/js/**").permitAll()
                  .antMatchers("/fonts/**").permitAll()
                  .antMatchers("/img/**").permitAll()
                  .antMatchers("/").permitAll()
                    .antMatchers("/api_v1/**").permitAll()
                  .anyRequest().authenticated()
                .and()
                  .formLogin().loginPage("/login")
                  .usernameParameter("login")
                  .defaultSuccessUrl("/authorize")
                  .failureUrl("/login?error")
                  .permitAll()
                .and()
                  .logout()
                  .logoutUrl("/logout")
                  .deleteCookies("remember-me")
                  .logoutSuccessUrl("/login")
                  .permitAll()
                .and()
                  .csrf()
                  .disable();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(this.userDetailsService);
            auth.authenticationProvider(this.authenticationProvider);
        }

    }

    @Configuration
    @Order(2)
    public static class RESTSecurityConfig extends WebSecurityConfigurerAdapter{

        @Value("${realm.type}")
        private String realm;

        private final UserDetailsService userDetailsService;
        private final AuthenticationProvider authenticationProvider;

        @Autowired
        public RESTSecurityConfig(UserDetailsService userDetailsService, AuthenticationProvider authenticationProvider) {
            this.userDetailsService = userDetailsService;
            this.authenticationProvider = authenticationProvider;
        }

        @Bean("restAuthenticationPoint")
        public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint(){
            return new RESTAuthenticationEntryPoint();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception{

            http.authorizeRequests()
                  .antMatchers("/api_v1/admin_role/**").hasAuthority("ADMIN")
                  .antMatchers( "/api_v1/student_role/**").hasAuthority("STUDENT")
                  .antMatchers("/api_v1/cooperator_role/**").hasAuthority("COOPERATOR")
                  .antMatchers("/api_v1/event/**").permitAll()
                  .antMatchers("/api_v1/organization/**").permitAll()
                  .antMatchers("/api_v1/schedule/**").permitAll()
                  .anyRequest().authenticated()
                 .and()
                  .httpBasic()
                  .realmName(this.realm)
                  .authenticationEntryPoint(basicAuthenticationEntryPoint())
                 .and()
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                  .csrf()
                  .disable();

        }

        @Override
        public void configure(WebSecurity web){
            web.ignoring()
               .antMatchers(HttpMethod.OPTIONS, "/**");
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(this.userDetailsService);
            auth.authenticationProvider(this.authenticationProvider);
        }

    }

}
