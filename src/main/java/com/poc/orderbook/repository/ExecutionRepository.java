package com.poc.orderbook.repository;

import com.poc.orderbook.entity.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Integer> {

    List<Execution> findByFidAndIsExecuted(int fid, Boolean executionStatus);
}
