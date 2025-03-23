package uva.rdmonitor.internal.shared.interfaces;

import java.time.LocalDateTime;

public interface GenericDtoProvider {
    Long getId();
    LocalDateTime getCreated();
    LocalDateTime getLastModified();
    Long getVersion();
}
