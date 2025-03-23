package uva.rdmonitor.internal.shared.interfaces;

import jakarta.persistence.OptimisticLockException;

import java.util.Objects;

public interface VersionableObject {
    Long getId();
    Long getVersion();

    default void checkVersion(Long version) {
        if (getId() == null || Objects.equals(this.getVersion(), version)) {
            return;
        }
        throw new OptimisticLockException(this.getClass().getName() + " version = " + this.getVersion() + ", expected version = " + version);
    }
}
