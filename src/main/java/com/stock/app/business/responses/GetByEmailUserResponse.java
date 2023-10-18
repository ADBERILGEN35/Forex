package com.stock.app.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByEmailUserResponse {
    private String userName;
    private String password;
    private String TCKN;
    private String email;

}