package io.pn.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		
		Object claim = jwt.getClaim("realm_access");
		System.out.println(claim.toString());
		
		@SuppressWarnings("unchecked")
		Map<String,Object> realmAcces = (Map<String, Object>) jwt.getClaims().get("realm_access");
		
		if(realmAcces == null || realmAcces.isEmpty())
		return new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<String> authorities = ((List<String>) realmAcces.get("roles")).stream().map(role -> "ROLE_"+role).toList();
		
		// put the roles into granted authority
		
		List<GrantedAuthority> listRoles = new ArrayList<>();
		authorities.stream().forEach(role -> listRoles.add(
					new SimpleGrantedAuthority(role)
				));
		
		
		
		return listRoles;
	}

}
