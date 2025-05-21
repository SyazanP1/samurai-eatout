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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
						.requestMatchers("/", "/login", "/restaurant/**", "/signup/**", "/member/resetPassword/**",
								"/stripe/webhook")
						.permitAll()
						//	静的リソースは誰でも閲覧可能とする
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						//						.requestMatchers("/admin/restaurant/**", "/admin/member/**", "/admin/category/**").hasRole("ADMIN")
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						//						.requestMatchers("/paidMember").hasAuthority("FREE")
						.anyRequest().authenticated())
				.formLogin((form) -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
//						Spring Securityの挙動に関して、デフォルトの挙動として、ユーザーが未ログインでアクセスしようとしていた保護されたURLに対して、ログインが成功した後にその元々アクセスしようとしていたページにリダイレクトされる動作を持っています。この動作は「元々のリクエストに戻す」という仕様として意図されています。
//						Spring Securityの formLogin()設定で alwaysUseDefaultTargetUrl(true)を使用します。この設定を有効にすると、ユーザーがログインするたびに defaultSuccessUrlに指定されたURLへリダイレクトされます。
//						.defaultSuccessUrl("/?loggedIn")
						.defaultSuccessUrl("/?loggedIn", true)
						.failureUrl("/login?error")
						.permitAll())
				.logout((logout) -> logout
						//	Getメソッドでログアウトできるようにする
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/?loggedOut")
						.permitAll())
				//				.csrf().ignoringRequestMatchers("/stripe/webhook");
				.csrf(csrf -> csrf.ignoringRequestMatchers("/stripe/webhook"));

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
