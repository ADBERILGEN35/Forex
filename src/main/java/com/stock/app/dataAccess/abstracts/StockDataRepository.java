package com.stock.app.dataAccess.abstracts;

import com.stock.app.entities.concretes.StockData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDataRepository extends JpaRepository<StockData,String> {
}