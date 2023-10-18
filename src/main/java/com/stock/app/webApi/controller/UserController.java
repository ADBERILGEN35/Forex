package com.stock.app.webApi.controller;

import com.stock.app.business.abstracts.UserService;
import com.stock.app.business.requests.CreateUserRequest;
import com.stock.app.business.requests.LoginUserRequest;
import com.stock.app.business.requests.UpdateUserRequest;
import com.stock.app.business.responses.GetAllUserResponse;
import com.stock.app.business.responses.GetByEmailUserResponse;
import com.stock.app.business.responses.GetByUserNameUserResponse;
import com.stock.app.business.responses.JwtResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping
    public List<GetAllUserResponse> getAll() {
        return userService.getAll();
    }


    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@RequestBody @Valid CreateUserRequest createUserRequest) {
        this.userService.add(createUserRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginUserRequest loginUserRequest) {
        try {
            String token = userService.auth(loginUserRequest);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(new JwtResponse("Username or password is wrong"));
        }
    }

    @PutMapping("/update")
    public void update(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        this.userService.update(updateUserRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        this.userService.delete(id);
    }

    @GetMapping("/email/{email}")
    public GetByEmailUserResponse getByEmailUserResponse(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/user/{userName}")
    public GetByUserNameUserResponse getByUserNameUserResponse(@PathVariable String userName) {
        return userService.getByUserName(userName);
    }

}