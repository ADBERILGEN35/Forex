package com.stock.app.business.rules;

import com.stock.app.core.utilies.exceptions.BusinnessException;
import com.stock.app.dataAccess.abstracts.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserBusinessRules {
    private UserRepository userRepository;

    public void checkIfUserEmailExists(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinnessException("User email already exists");
        }
    }

    public void checkIfUserNameExists(String name) {
        if (this.userRepository.existsByUserName(name)) {
            throw new BusinnessException("Username already exists");
        }
    }
}