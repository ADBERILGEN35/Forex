package com.stock.app.webApi.controller;

import com.stock.app.business.abstracts.StockDataService;
import com.stock.app.business.requests.CreateStockDataRequest;
import com.stock.app.business.responses.GetAllStockDataResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-data")
@AllArgsConstructor
public class StockDataController {
    private StockDataService stockDataService;

    @PostMapping("save-stock")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add() {
        this.stockDataService.saveAllStockData();
    }

    @GetMapping
    public List<GetAllStockDataResponse> getAll() {
        return stockDataService.getAll();
    }
}