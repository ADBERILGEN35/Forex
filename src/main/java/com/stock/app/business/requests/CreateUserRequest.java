package com.stock.app.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 25)
    private String userName;

    @NotNull
    @NotBlank
    private String password;

    private String tckn;

    @NotNull
    @NotBlank
    private String email;

}