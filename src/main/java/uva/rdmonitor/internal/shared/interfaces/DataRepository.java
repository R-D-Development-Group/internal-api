package uva.rdmonitor.internal.shared.interfaces;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import uva.rdmonitor.internal.shared.objects.ProjectionProvider;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface DataRepository<T, Q, ID> extends JpaRepository<T, ID> {
    T findByIdSafe(ID id);
    T findForUpdate(ID id, Long version);
    T findForUpdate(QuerydslSpecification<T> specification, Long version);
    void deleteByIdAndVersion(ID id, Long entityVersion);
    Optional<T> findOne(QuerydslSpecification<T> specification);
    T findOneSafe(QuerydslSpecification<T> specification);
    Optional<T> fetchFirst(QuerydslSpecification<T> specification);
    List<T> findAll(QuerydslSpecification<T> specification);
    List<T> findAll(QuerydslSpecification<T> specification, Sort sort);
    List<T> findAll(QuerydslSpecification<T> specification, OrderSpecifier<?>... orders);
    List<T> findAll(OrderSpecifier<?>... orders);
    <R> List<R> findAll(QuerydslSpecification<T> specification, ProjectionProvider<R> projectionProvider);
    <R> List<R> findAll(QuerydslSpecification<T> specification, ProjectionProvider<R> projectionProvider, OrderSpecifier<?>... orders);
    <R> List<R> findAll(QuerydslSpecification<T> specification, ProjectionProvider<R> projectionProvider, Sort sort);
    Page<T> findPage(QuerydslSpecification<T> specification, Pageable pageable);
    <R> Page<R> findPage(QuerydslSpecification<T> specification, Pageable pageable, ProjectionProvider<R> projectionProvider);
    Page<T> findPage(QuerydslSpecification<T> specification, PageRequestProvider pageRequestProvider);
    <R> Page<R> findPage(QuerydslSpecification<T> specification, PageRequestProvider pageRequestProvider, ProjectionProvider<R> projectionProvider);
    long count(QuerydslSpecification<T> specification);
    boolean exists(QuerydslSpecification<T> specification);
    void detach(T entity);
    void deleteAll(QuerydslSpecification<T> specification);
    void refresh(T entity);
    List<ID> findAllIds(QuerydslSpecification<T> specification);
}
