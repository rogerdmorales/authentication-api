package br.com.roger.authenticationapi.configuration;

import br.com.roger.authenticationapi.security.CustomAuthenticationEntryPoint;
import br.com.roger.authenticationapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure( AuthenticationManagerBuilder authenticationManagerBuilder ) throws Exception {
        authenticationManagerBuilder
                .userDetailsService( customUserDetailsService )
                .passwordEncoder( passwordEncoder() );
    }

    @Bean( BeanIds.AUTHENTICATION_MANAGER )
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        // Without this configuration is not possible to access h2-console
        http.headers().frameOptions().disable();

        http.csrf().disable().cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint( authenticationEntryPoint )
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console" )
                .permitAll()
                .antMatchers( HttpMethod.POST, "/api/auth/login", "/api/auth/users" )
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure( WebSecurity web ) throws Exception {
        web.ignoring()
                .antMatchers( HttpMethod.OPTIONS, "/**" );
    }
}
