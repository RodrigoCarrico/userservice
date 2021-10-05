package com.demo.userservice.service;

import com.demo.userservice.domain.Role;
import com.demo.userservice.domain.User;
import com.demo.userservice.utils.ConfigIT;
import com.demo.userservice.utils.Factory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("UserServiceImpl Test")
public class UserServiceImplIT extends ConfigIT {

    @Test
    public void deveIncluirNovoUser() {
        // then
        User user = User.builder().username("teste")
                .name("teste")
                .password("123")
                .build();

        // when
        userService.saveUser(user);

        // given
        User userPersisted = userRepository.findByUsername(user.getUsername());

        assertThat(user.getUsername()).isEqualTo(userPersisted.getUsername());
        assertThat(user.getName()).isEqualTo(userPersisted.getName());
        assertThat(user.getPassword()).isEqualTo(userPersisted.getPassword());
        assertThat(user.getRoles()).isNotNull();
    }

    @Test
    public void deveIncluirNovaRole() {
        // then
        Role role = Role.builder()
                .name("teste")
                .build();

        // when
        userService.saveRole(role);

        // given
        Role rolePersisted = roleRepository.findByName(role.getName());

        assertThat(role.getName()).isEqualTo(rolePersisted.getName());
        assertThat(role.getId()).isNotNull();
    }

    @Test
    public void deveIncluirNovaRoleEmUser() {
        // then
        Role role = Factory.persist(roleRepository, Role.builder()
                .name("teste")
                .build());

        User user = Factory.persist(userRepository, User.builder()
                .username("teste")
                .name("teste")
                .password("123")
                .build());


        // when
        userService.addRoleToUser(user.getUsername(), role.getName());

        // given
        User userPersisted = userRepository.getById(user.getId());
        assertThat(user.getRoles()).hasSize(1);
        assertThat(user.getRoles()).contains(role);

    }

    @Test
    public void devePesquisarUmUser() {
        // given
        User user = Factory.persist(userRepository, Factory.oneUserBuild().build());

        // when
        User userPersisted = userService.getUser(user.getUsername());

        // then
        assertThat(userPersisted).isNotNull();
    }

    @Test
    public void devePesquisarTodosUsuarios() {
        // given
        User user = Factory.persist(userRepository, Factory.oneUserBuild().build());

        // when
        List<User> users = userService.getUsers();

        // then
        assertThat(users).isNotNull();
        assertThat(users).hasSizeGreaterThan(0);

    }

    @Test
    public void deveCarregarAsCredenciasDoUsuarioUsandoCredenciaisDoSpring() {
        // given
        User user = Factory.persist(userRepository, Factory.oneUserBuild().build());
        Role role = Factory.persist(roleRepository, Factory.oneRoleBuild().build());
        user.getRoles().add(role);

        // when
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        // then
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());

        userDetails.getAuthorities().stream().forEach(grantedAuthority ->
                assertThat(grantedAuthority.getAuthority()).isIn(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        );
    }


    @Test
    public void naoDeveCarregarAsCredenciasDoUsuarioUsandoCredenciaisDoSpring() {
        // when - then
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() ->
                userService.loadUserByUsername("userFake")
        );
    }
}
