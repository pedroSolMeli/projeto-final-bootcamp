package com.mercadolivre.projetointegrador.inboundorder.repository;

import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("InboundOrderRepository")
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {

    InboundOrder getInboundOrderByOrderNumber(Long orderNumber);

}
