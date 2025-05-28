package com.task_manager.zhsaidk.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 2, max = 32)
    private String firstName;

    @Length(min = 2, max = 32)
    private String lastName;

    @Email
    @Column(length = 256, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 256, nullable = false)
    private String password;
}
