package com.mercadolivre.projetointegrador.warehouse.repository;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("WarehouseRepository")
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse getWarehouseByCode(String code);

    Optional<Warehouse> findWarehouseByCode(String code);
}
