package com.task_manager.zhsaidk.service;

import com.task_manager.zhsaidk.database.entity.User;
import com.task_manager.zhsaidk.database.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found. Authentication failed!"));

        return UserDetailsImpl.create(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                Set.of(user.getRole()),
                user.getPassword()
        );
    }
}