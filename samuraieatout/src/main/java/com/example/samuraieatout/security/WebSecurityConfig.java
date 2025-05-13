package com.example.samuraieatout.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
//						参考サイト https://zenn.dev/peishim/articles/c225ac5a5eedb0
//						.requestMatchers("/css/**", "/login", "/signup/**", "/stripe/webhook", "/member/resetPassword/**", "/member/resetPassword/inputPassword/verify/**", "/home").permitAll()
						.requestMatchers("/home", "/login", "/restaurant/**", "/signup/**", "/member/resetPassword/**", "/stripe/webhook").permitAll()
						//	静的リソースは誰でも閲覧可能とする
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/paidMember").hasAuthority("FREE")
						.anyRequest().authenticated())
				.formLogin((form) -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/home")
						.failureUrl("/login?error")
						.permitAll())
				.logout((logout) -> logout
//						.logoutSuccessUrl("/?loggedOut")	//	"/"へのアクセスに対して応答するコントローラーがを定義していないのでwhitepageエラーが発生。以下のように修正。
						.logoutSuccessUrl("/login?loggedOut")
						.permitAll())
				.csrf().ignoringRequestMatchers("/stripe/webhook");

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
