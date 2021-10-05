package com.demo.userservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Role test")
public class RoleTest {
    @Test
    public void deveConstruirRole() {
        // give
        Role role = Role.builder().name("role").build();

        // then
        Assertions.assertThat(role).isNotNull();
        Assertions.assertThat(role.getId()).isNull();
        Assertions.assertThat(role.getName()).isNotNull();

    }

    @Test
    public void naoDeveConstruirRoleSemCaposObrigatorios() {
        // given - then
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
                Role.builder().build());
    }
}
