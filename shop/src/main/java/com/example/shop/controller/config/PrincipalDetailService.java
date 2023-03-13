package com.example.shop.controller.config;

import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    final
    UserRepository userRepository;

    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User principal = userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당유저를 찾을 수 없습니다."));
        return new PrincipalDetail(principal);
    }

}
