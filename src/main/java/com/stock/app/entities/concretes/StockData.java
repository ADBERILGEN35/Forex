package com.stock.app.entities.concretes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stock_data")
public class StockData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "text")
    private String text;

    @Column(name = "lastprice")
    private Double lastprice;

}