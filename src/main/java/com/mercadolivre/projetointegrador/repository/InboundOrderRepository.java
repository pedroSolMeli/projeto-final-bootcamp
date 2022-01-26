package com.mercadolivre.projetointegrador.repository;

import com.mercadolivre.projetointegrador.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {

}
