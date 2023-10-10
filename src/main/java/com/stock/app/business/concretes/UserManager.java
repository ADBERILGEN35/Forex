package com.stock.app.business.concretes;

import com.stock.app.business.abstracts.UserService;
import com.stock.app.business.requests.CreateUserRequest;
import com.stock.app.business.requests.LoginUserRequest;
import com.stock.app.business.requests.UpdateUserRequest;
import com.stock.app.business.responses.GetAllUserResponse;
import com.stock.app.business.responses.GetByEmailUserResonse;
import com.stock.app.business.responses.GetByIdUserResonse;
import com.stock.app.business.rules.UserBusinessRules;
import com.stock.app.business.security.JwtTokenProvider;
import com.stock.app.core.utilies.mappers.ModelMapperService;
import com.stock.app.dataAccess.abstracts.UserRepository;
import com.stock.app.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserManager implements UserService {
    private UserRepository userRepository;
    private ModelMapperService modelMapperService;
    private UserBusinessRules userBusinessRules;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private ForexUserDetailManager forexUserDetailManager;
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public List<GetAllUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetAllUserResponse> userResponses = users.stream()
                .map(usr -> this.modelMapperService.forResponse()
                        .map(usr, GetAllUserResponse.class)).collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public List<GetByEmailUserResonse> getByEmail(String email) {
        List<User> userList = userRepository.findByEmail(email);
        return userList.stream()
                .map(usr -> this.modelMapperService.forResponse()
                        .map(usr, GetByEmailUserResonse.class)).collect(Collectors.toList());
    }

    @Override
    public GetByIdUserResonse getById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();
        GetByIdUserResonse resonse = this.modelMapperService.forResponse()
                .map(user, GetByIdUserResonse.class);
        return resonse;
    }

    @Override
    public void add(CreateUserRequest createUserRequest) {
        this.userBusinessRules.checkIfUserEmailExists(createUserRequest.getEmail());
        this.userBusinessRules.checkIfUserNameExists(createUserRequest.getUserName());

        User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        user.setPassword(encodedPassword);
        this.userRepository.save(user);
    }

    @Override
    public void update(UpdateUserRequest updateUserRequest) {
        User user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);
        this.userRepository.save(user);
    }

    @Override
    public String auth(LoginUserRequest loginUserRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserRequest.getEmail(),
                            loginUserRequest.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateToken(authentication);

            return token;

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Kullanıcı adı veya şifre hatalı");
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}