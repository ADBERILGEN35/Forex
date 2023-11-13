package com.stock.app.dataAccess.abstracts;

import com.stock.app.entities.concretes.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockDataRepository extends JpaRepository<StockData, String> {
    @Transactional
    void deleteByCode(String code);

    @Query("SELECT r FROM StockData r WHERE r.code LIKE %:code%")
    List<StockData> findByCode(String code);

    @Modifying
    @Transactional
    @Query("UPDATE StockData r SET r.lastprice = :lastprice WHERE r.code = :code")
    void updateStockDataLastPrice(String code, Double lastprice);

}