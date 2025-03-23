package uva.rdmonitor.internal.authentication.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractRoles(jwt).stream()
                ).collect(Collectors.toList());

        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    private String getPrincipalClaimName(Jwt jwt) {
        var claimName = JwtClaimNames.SUB;
        if(principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> roles;

        if(jwt.getClaim("resource_access") == null) {
            return Set.of();
        }

        resourceAccess = jwt.getClaim("resource_access");
        if(resourceAccess.get(resourceId) == null) {
            return Set.of();
        }

        resource = (Map<String, Object>) resourceAccess.get(resourceId);
        roles = (Collection<String>) resource.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
