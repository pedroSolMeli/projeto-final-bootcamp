package com.mercadolivre.projetointegrador.batch.repository;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("BatchRepository")
public interface BatchRepository extends JpaRepository<Batch, Long>{

    Batch getBatchByBatchNumber(Long batchNumber);
}
