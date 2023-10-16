package com.stock.app.business.requests;

import com.stock.app.entities.concretes.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    @NotEmpty
    private Set<UserRole> roles;

    private boolean isEnabled;
}
