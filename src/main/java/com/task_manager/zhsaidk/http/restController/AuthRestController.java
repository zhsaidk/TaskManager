package com.task_manager.zhsaidk.http.restController;

import com.task_manager.zhsaidk.database.entity.Token;
import com.task_manager.zhsaidk.database.entity.User;
import com.task_manager.zhsaidk.database.repo.TokenRepository;
import com.task_manager.zhsaidk.database.repo.UserRepository;
import com.task_manager.zhsaidk.dto.AuthRequest;
import com.task_manager.zhsaidk.dto.RefreshRequest;
import com.task_manager.zhsaidk.dto.RefreshResponse;
import com.task_manager.zhsaidk.dto.ResponseRequest;
import com.task_manager.zhsaidk.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest loginRequest){
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            User user = userRepository.findByUsername(authenticate.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            String accessToken = jwtUtils.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());
            tokenRepository.save(
                    Token.builder()
                            .user(user)
                            .refreshToken(refreshToken)
                            .build()
            );
            return ResponseEntity.ok(new ResponseRequest(accessToken, refreshToken));
        }catch (AuthenticationException exception){
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request){
        String refreshToken = request.getRefresh_token();
        if (!StringUtils.hasText(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh token");
        }
        Optional<Token> rToken = tokenRepository.findByRefreshToken(refreshToken);
        if (rToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        if (!jwtUtils.isValidToken(refreshToken)){
            tokenRepository.delete(rToken.get());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Expired or invalid refresh token");
        }

        String username = jwtUtils.getUsername(refreshToken);
        String generateAccessToken = jwtUtils.generateAccessToken(username);

        return ResponseEntity.ok(new RefreshResponse(generateAccessToken));
    }
}
