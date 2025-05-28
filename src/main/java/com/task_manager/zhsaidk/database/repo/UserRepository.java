package com.task_manager.zhsaidk.database.repo;

import com.task_manager.zhsaidk.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer > {
}
