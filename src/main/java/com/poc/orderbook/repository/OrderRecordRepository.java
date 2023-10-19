package com.poc.orderbook.repository;

import com.poc.orderbook.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecord, String> {

    List<OrderRecord> findByObidAndFidAndIsCompleteOrderByEntryDate(int obid, int fid, Boolean completionStatus);

}
