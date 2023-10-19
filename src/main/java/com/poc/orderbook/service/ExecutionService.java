package com.poc.orderbook.service;

import com.poc.orderbook.entity.Execution;
import com.poc.orderbook.exceptionhandler.BaseException;

import java.util.List;

public interface ExecutionService {

    List<Execution> executeOrderByFid(int obid, int fid) throws BaseException;
}
