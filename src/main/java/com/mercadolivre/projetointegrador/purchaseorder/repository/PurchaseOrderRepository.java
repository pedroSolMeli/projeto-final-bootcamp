package com.mercadolivre.projetointegrador.purchaseorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;

@Repository("PurchaseOrderRepository")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long >{

}
