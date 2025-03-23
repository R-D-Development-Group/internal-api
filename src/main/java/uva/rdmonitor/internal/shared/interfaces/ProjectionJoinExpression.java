package uva.rdmonitor.internal.shared.interfaces;

import com.querydsl.jpa.JPQLQuery;

@FunctionalInterface
public interface ProjectionJoinExpression {
    void join(JPQLQuery<?> query);
}
