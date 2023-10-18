package com.stock.app.business.concretes;

import com.stock.app.business.abstracts.UserService;
import com.stock.app.business.requests.CreateUserRequest;
import com.stock.app.business.requests.LoginUserRequest;
import com.stock.app.business.requests.UpdateUserRequest;
import com.stock.app.business.responses.GetAllUserResponse;

import com.stock.app.business.responses.GetByEmailUserResponse;
import com.stock.app.business.responses.GetByUserNameUserResponse;
import com.stock.app.business.rules.UserBusinessRules;
import com.stock.app.business.security.JwtService;
import com.stock.app.core.utilies.mappers.ModelMapperService;
import com.stock.app.dataAccess.abstracts.UserRepository;
import com.stock.app.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public List<GetAllUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetAllUserResponse> userResponses = users.stream()
                .map(usr -> this.modelMapperService.forResponse()
                        .map(usr, GetAllUserResponse.class)).collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public GetByEmailUserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        GetByEmailUserResponse getByEmailUserResponse = this.modelMapperService.forResponse()
                .map(user, GetByEmailUserResponse.class);
        return getByEmailUserResponse;
    }

    @Override
    public GetByUserNameUserResponse getByUserName(String userName) {
        User user = this.userRepository.findByUserName(userName);
        GetByUserNameUserResponse getByIdUserResponse = this.modelMapperService.forResponse()
                .map(user, GetByUserNameUserResponse.class);
        return getByIdUserResponse;
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
                            loginUserRequest.getUserName(),
                            loginUserRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails);

            return token;
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UsernameNotFoundException("Username or password is wrong");
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}