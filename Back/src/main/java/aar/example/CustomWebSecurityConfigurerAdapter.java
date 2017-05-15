package aar.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import aar.example.auth.filter.JWTFilter;


@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Bean
    public JWTFilter getJWTFilter() throws Exception {
        return new JWTFilter();
    }
	
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
    
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        	.withUser("admin").password("admin").roles("ADMIN","USER");	
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()		
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()			
			.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and()
			.authorizeRequests().antMatchers("/auth/**").permitAll()			
			.anyRequest().authenticated();
		
	    http.addFilterAfter(
    			getJWTFilter(),
    			BasicAuthenticationFilter.class);
	}
	
}