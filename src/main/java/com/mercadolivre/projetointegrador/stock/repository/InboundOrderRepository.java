package com.mercadolivre.projetointegrador.stock.repository;

import com.mercadolivre.projetointegrador.stock.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {

}
