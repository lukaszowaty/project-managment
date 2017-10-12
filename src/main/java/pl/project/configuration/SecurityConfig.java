package pl.project.configuration;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pl.project.service.impl.UserServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	private static final String SALT = "salt";
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/webjars/**",
			"/css/**",
			"/",
			"/signup"
	};
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated()
			.and()
			.authorizeRequests().antMatchers("/projects", "/contact").access("hasRole('ROLE_USER')")
			.and()
			.formLogin().failureUrl("/index?error").loginPage("/loginUser")
			.and()
			.authorizeRequests().antMatchers("/admin/*").access("hasRole('ROLE_ADMIN')")
			.and()
			.formLogin().loginPage("/loginUser");
		
        http
        	.csrf().disable()
            .formLogin().failureUrl("/loginUser?error").defaultSuccessUrl("/").loginPage("/loginUser").permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
            .and()
            .rememberMe();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
	}
}
