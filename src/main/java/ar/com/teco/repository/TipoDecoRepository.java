package ar.com.teco.repository;

import ar.com.teco.domain.TipoDeco;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoDeco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDecoRepository extends JpaRepository<TipoDeco, Long>, JpaSpecificationExecutor<TipoDeco> {
}
