package io.pn.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	
	@Autowired
	private KeyCloakConverter keyCloakConverter;
	
	@Bean
	protected SecurityFilterChain config(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(req ->
			req.requestMatchers(HttpMethod.GET, "/user/api/check")
			// here we can set the scope but we need to define multiple scopes
			 //  .hasAnyAuthority("SCOPE_profile")
				// if we have more then one roles are there then we can define
			  //  .hasAnyRole("developer","qa")
				.hasRole("developer")
			
			   .requestMatchers("/token").authenticated()
			   
				);
		
		// here we are providing oauth2ResourceServer with jwt and inside the jwt the role we are defining here
		http.oauth2ResourceServer(token -> token.jwt(
				jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
				));
		
		return http.build();
		
	}
	
	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthConvertor = new JwtAuthenticationConverter();
		jwtAuthConvertor.setJwtGrantedAuthoritiesConverter(keyCloakConverter);
		return jwtAuthConvertor;
	}
}
