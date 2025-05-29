package com.task_manager.zhsaidk.service;

import com.task_manager.zhsaidk.database.entity.Role;
import com.task_manager.zhsaidk.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private Set<Role> roles;
    private String password;

    public static UserDetailsImpl create(Integer id,
                                         String firstName,
                                         String lastName,
                                         String username,
                                         Set<Role> roles,
                                         String password){
        return new UserDetailsImpl(
                id, firstName, lastName, username, roles, password
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
