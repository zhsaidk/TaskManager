package com.task_manager.zhsaidk.database.repo;

import com.task_manager.zhsaidk.database.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByRefreshToken(String refreshToken);
}
