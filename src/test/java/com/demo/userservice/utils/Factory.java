package com.demo.userservice.utils;

import com.demo.userservice.domain.Role;
import com.demo.userservice.domain.User;
import com.demo.userservice.repository.RoleRepository;
import com.demo.userservice.repository.UserRepository;

public class Factory {

    public static User persist(UserRepository repository, User user) {
        return repository.saveAndFlush(user);
    }

    public static Role persist(RoleRepository repository, Role role) {
        return repository.save(role);
    }

    public static User.UserBuilder oneUserBuild() {
        return User.builder().username("user").name("user").password("123456");
    }

    public static Role.RoleBuilder oneRoleBuild() {
        return Role.builder().name("role");
    }


}
