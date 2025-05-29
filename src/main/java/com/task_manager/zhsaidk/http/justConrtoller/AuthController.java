package com.task_manager.zhsaidk.http.justConrtoller;

import com.task_manager.zhsaidk.dto.UserCreateDto;
import com.task_manager.zhsaidk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "user/auth";
    }

    @GetMapping("/register")
    public String registrationPage(){
        return "user/registration";
    }

    @PostMapping("/register")
    public String registration(@Valid UserCreateDto userDto,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "redirect:/register";
        }
        userService.createUser(userDto);
        return "redirect:/login?register=true";
    }
}
