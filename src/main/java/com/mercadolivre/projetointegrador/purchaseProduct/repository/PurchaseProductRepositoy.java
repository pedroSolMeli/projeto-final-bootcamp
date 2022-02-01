package com.mercadolivre.projetointegrador.purchaseProduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;

@Repository("PurchaseProduct")
public interface PurchaseProductRepositoy extends JpaRepository<PurchaseProduct, Long> {

}
