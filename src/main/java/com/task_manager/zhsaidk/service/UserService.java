package com.task_manager.zhsaidk.service;

import com.task_manager.zhsaidk.database.entity.Role;
import com.task_manager.zhsaidk.database.entity.User;
import com.task_manager.zhsaidk.database.repo.UserRepository;
import com.task_manager.zhsaidk.dto.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserCreateDto userCreateDto){
        User currentUser = User.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .username(userCreateDto.getUsername())
                .role(Role.USER)
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .build();
        userRepository.save(currentUser);
    }
}
