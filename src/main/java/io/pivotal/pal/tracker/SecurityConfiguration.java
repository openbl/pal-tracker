package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final boolean disableHttps;
    private final String username = "user";
    private final String password = "password";

    public SecurityConfiguration(@Value("${https.disabled}") boolean disableHttps) {
        this.disableHttps = disableHttps;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!disableHttps) {
            http.requiresChannel().anyRequest().requiresSecure();
        }

        http.authorizeRequests().antMatchers("/**").hasRole("USER")
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.inMemoryAuthentication()
                .withUser(username).password(password).roles("USER");
    }
}