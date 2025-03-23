package uva.rdmonitor.internal.authentication.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import uva.rdmonitor.internal.authentication.appUser.entity.AppUserEntity;
import uva.rdmonitor.internal.authentication.appUser.repository.AppUserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class StaticAdminAuditorAware implements AuditorAware<AppUserEntity> {

    private final AppUserRepository addUserRepository;

    @Override
    public @NonNull Optional<AppUserEntity> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        // TODO: Currently this is a hack. Needs refactoring because the principal is not a JWT instance
        var principal = (Jwt) authentication.getPrincipal();

        if (principal != null) {
            Long appUserId = principal.getClaim("appUserId");

            if (appUserId != null) {
                return addUserRepository.findById(appUserId)
                        .or(() -> addUserRepository.findById(1L)); // Default system user
            }
        }

        return Optional.empty();
    }
}