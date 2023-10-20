package com.stock.app.business.abstracts;

import com.stock.app.business.requests.CreateStockDataRequest;
import com.stock.app.business.responses.GetAllStockDataResponse;
import com.stock.app.business.responses.GetAllUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockDataService {

    void saveAllStockData();

    List<GetAllStockDataResponse> getAll();

}