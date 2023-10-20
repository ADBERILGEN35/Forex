package com.stock.app.business.concretes;

import com.stock.app.business.SymbolDefinitionClient;
import com.stock.app.business.abstracts.StockDataService;
import com.stock.app.business.responses.GetAllStockDataResponse;
import com.stock.app.business.responses.GetAllUserResponse;
import com.stock.app.core.utilies.mappers.ModelMapperService;
import com.stock.app.dataAccess.abstracts.StockDataRepository;
import com.stock.app.entities.concretes.StockData;
import com.stock.app.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockDataManager implements StockDataService {
    private SymbolDefinitionClient symbolDefinitionClient;
    private StockDataRepository stockDataRepository;
    private ModelMapperService modelMapperService;


    @Override
    public void saveAllStockData() {
        List<GetAllStockDataResponse> stockDataList = symbolDefinitionClient.getStockDataList();

        stockDataList.forEach(stockDataResponse -> {
            String code = stockDataResponse.getCode();
            List<StockData> existingStockDataList = stockDataRepository.findByCode(code);

            if (!existingStockDataList.isEmpty()) {
                for (StockData existingStockData : existingStockDataList) {
                    existingStockData.setLastprice(stockDataResponse.getLastprice());
                    stockDataRepository.save(existingStockData);
                }
            } else {
                StockData stockData = new StockData();
                stockData.setCode(stockDataResponse.getCode());
                stockData.setText(stockDataResponse.getText());
                stockData.setLastprice(stockDataResponse.getLastprice());

                stockDataRepository.save(stockData);
            }
        });
    }

    @Override
    public List<GetAllStockDataResponse> getAll() {
        List<StockData> stockData = stockDataRepository.findAll();
        List<GetAllStockDataResponse> stockDataResponses = stockData.stream()
                .map(stck -> this.modelMapperService.forResponse()
                        .map(stck, GetAllStockDataResponse.class)).collect(Collectors.toList());
        return stockDataResponses;
    }

}