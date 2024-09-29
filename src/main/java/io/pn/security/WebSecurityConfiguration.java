package io.pn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

// We were using GlobalMethodSecurity but right now 
// @EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)

// here @EnableMethodSecurity by default enable prePostEnabled = true
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {

	@Autowired
	private KeyCloakConverter keyCloakConverter;

	@Bean
	protected SecurityFilterChain config(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(req -> req.requestMatchers(HttpMethod.GET, "/user/api/**")
				// here we can set the scope but we need to define multiple scopes
				// .hasAnyAuthority("SCOPE_profile")
				// if we have more then one roles are there then we can define
				// .hasAnyRole("developer","qa")

				 .hasRole("developer")
				
				//.authenticated()
				// Here we can provide hasAuthority method, but we need to add the `ROLE`
				// .hasAuthority("ROLE_developer")
				.requestMatchers(HttpMethod.DELETE,"/user/delete/**")
				.authenticated()
				.requestMatchers(HttpMethod.GET,"/user/get/**")
				.authenticated()
				.anyRequest()
				.authenticated()

		);
		
	//	http.authorizeHttpRequests(req -> req.requestMatchers(HttpMethod.DELETE,"/user/delete/**").authenticated());
		
		// here we are providing oauth2ResourceServer with jwt and inside the jwt the
		// role we are defining here
		http.oauth2ResourceServer(
				token -> token.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

		return http.build();

	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthConvertor = new JwtAuthenticationConverter();
		jwtAuthConvertor.setJwtGrantedAuthoritiesConverter(keyCloakConverter);
		return jwtAuthConvertor;
	}
}
