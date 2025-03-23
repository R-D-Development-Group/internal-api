package uva.rdmonitor.internal.shared.interfaces;

import org.springframework.data.domain.PageRequest;

@FunctionalInterface
public interface PageRequestProvider {
    PageRequest toPageRequest();
}

