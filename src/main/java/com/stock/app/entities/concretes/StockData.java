package com.stock.app.entities.concretes;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StockData {
    private String _id;
    private String ticker;
    private String securityDesc;
    private String country;
    private String currency;
    private int precision;
    private String marketSector;
    private String exchange;
    private String legacyCode;
    private String pttRow;
    private int lotSize;
    private boolean shortSellEnabled;
    private int minimumLot;
    private int maximumLot;
    private String status;
    private long listingDate;
    private String grossSettl;
    private String unit;
    private String security;
    private String type;
    private String securityType;
    private String code;
    private String domain;
    private String issuer;
    private String issuerName;
    private String subMarket;
    private String subMarketDesc;
    private String market;
    private String marketDesc;
    private String ISIN;
    private String sector;
    private List<String> index;
    private Map<String, IndexWeight> indexWeight;
    private long lastUpdateTime;


    public static class IndexWeight {
        private String tnos;
        private long tnosd;
        private String ffr;
        private double ffrd;
        private String ffs;
        private long ffsd;
        private String wf;
        private int wfd;
        private String w;
        private double wd;

    }
}
