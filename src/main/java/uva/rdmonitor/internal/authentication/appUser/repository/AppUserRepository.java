package uva.rdmonitor.internal.authentication.appUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uva.rdmonitor.internal.authentication.appUser.entity.AppUserEntity;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
}