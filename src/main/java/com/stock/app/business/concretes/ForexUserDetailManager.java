package com.stock.app.business.concretes;

import com.stock.app.dataAccess.abstracts.UserRepository;
import com.stock.app.entities.concretes.User;
import com.stock.app.entities.concretes.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ForexUserDetailManager implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User mot found : " + username);
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is inactive : " + username);
        }

        Collection<? extends GrantedAuthority> authorities = mapAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }

    private Set<SimpleGrantedAuthority> mapAuthorities(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }
}
