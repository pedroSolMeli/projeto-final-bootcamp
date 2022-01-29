package com.mercadolivre.projetointegrador.warehouse.repository;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("WarehouseRepository")
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query( "select w from Warehouse w where w.code = :code")
    Warehouse findWarehouseByCode(@Param("code") String code);

}
