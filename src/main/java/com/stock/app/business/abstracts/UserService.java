package com.stock.app.business.abstracts;

import com.stock.app.business.requests.CreateUserRequest;
import com.stock.app.business.requests.LoginUserRequest;
import com.stock.app.business.requests.UpdateUserRequest;
import com.stock.app.business.responses.GetAllUserResponse;
import com.stock.app.business.responses.GetByEmailUserResonse;
import com.stock.app.business.responses.GetByIdUserResonse;
import com.stock.app.entities.concretes.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<GetAllUserResponse> getAll();

    List<GetByEmailUserResonse> getByEmail(String email);

    GetByIdUserResonse getById(Long id);

    void add(CreateUserRequest createUserRequest);

    void update(UpdateUserRequest updateUserRequeste);

    String auth(LoginUserRequest loginUserRequest);

    void delete(Long id);
}