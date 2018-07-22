package com.planeta.conciencia.contabilidad.repository;

import com.planeta.conciencia.contabilidad.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    @Query("SELECT f FROM Factura f WHERE f.created BETWEEN :fechaInicio AND :fechaFin")
    Optional<List<Factura>> getFacturasFrom(final @Param("fechaInicio") ZonedDateTime fechaInicio,
                                            final @Param("fechaFin") ZonedDateTime fechaFin);
}
