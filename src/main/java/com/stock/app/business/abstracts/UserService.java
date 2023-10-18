package com.stock.app.business.abstracts;

import com.stock.app.business.requests.CreateUserRequest;
import com.stock.app.business.requests.LoginUserRequest;
import com.stock.app.business.requests.UpdateUserRequest;
import com.stock.app.business.responses.GetAllUserResponse;
import com.stock.app.business.responses.GetByEmailUserResponse;
import com.stock.app.business.responses.GetByUserNameUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<GetAllUserResponse> getAll();

    GetByEmailUserResponse getByEmail(String email);

    GetByUserNameUserResponse getByUserName(String userName);

    void add(CreateUserRequest createUserRequest);

    void update(UpdateUserRequest updateUserRequeste);

    String auth(LoginUserRequest loginUserRequest);

    void delete(Long id);
}