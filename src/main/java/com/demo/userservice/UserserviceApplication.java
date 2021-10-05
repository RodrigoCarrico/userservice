package com.demo.userservice;

import com.demo.userservice.domain.Role;
import com.demo.userservice.domain.User;
import com.demo.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Caso queira fazer uma carga no sistema use este script para incluir dados na tabela
     *
     * @param userService
     * @return
     */

   /* @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(Role.builder().name("ROLE_USER").build());
            userService.saveRole(Role.builder().name("ROLE_MANAGER").build());
            userService.saveRole(Role.builder().name("ROLE_SUPER_ADMIN").build());

            userService.saveUser(User.builder().name("Jhon Travolta").username("jhon").password("1234").build());
            userService.saveUser(User.builder().name("Will Smith").username("will").password("1234").build());
            userService.saveUser(User.builder().name("Jim Carry").username("jim").password("1234").build());


            userService.addRoleToUser("jhon", "ROLE_USER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
        };
    }*/

}
