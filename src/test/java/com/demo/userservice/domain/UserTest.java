package com.demo.userservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("User test")
public class UserTest {
    @Test
    public void deverConstruirUser(){
        // given
        User user = User.builder()
                .password("123")
                .name("test")
                .username("test")
                .build();

        // then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getUsername()).isNotNull();
        assertThat(user.getName()).isNotNull();
        assertThat(user.getRoles()).isNotNull();

    }

    @Test
    public void naoDeverConstruirUserComCamposVazios(){
        // given - then
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
            User.builder().build());
    }
}
