package com.mercadolivre.projetointegrador.inboundorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;

@Repository("InboundOrderRepository")
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {

}
