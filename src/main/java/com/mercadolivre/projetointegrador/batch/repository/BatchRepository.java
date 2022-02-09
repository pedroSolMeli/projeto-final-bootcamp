package com.mercadolivre.projetointegrador.batch.repository;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("BatchRepository")
public interface BatchRepository extends JpaRepository<Batch, Long>{

    Batch getBatchByBatchNumber(Long batchNumber);
    List<Batch> getBatchsByinboundOrder_Section_Id(Long sectionId);
    List<Batch> getBatchesByProduct_ProductType(ProductType productType);
}
