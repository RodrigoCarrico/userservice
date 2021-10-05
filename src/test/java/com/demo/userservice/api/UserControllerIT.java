package com.demo.userservice.api;

import com.demo.userservice.api.dto.RoleDto;
import com.demo.userservice.api.dto.RoleToUserDto;
import com.demo.userservice.api.dto.UserDto;
import com.demo.userservice.domain.Role;
import com.demo.userservice.domain.User;
import com.demo.userservice.utils.AdapterConfigIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.demo.userservice.utils.Factory.*;
import static com.demo.userservice.utils.TestUtils.objectToJson;
import static com.demo.userservice.utils.TestUtils.userToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController test")
public class UserControllerIT extends AdapterConfigIT {

    @Autowired
    private MockMvc mock;

    private void setupUsers() {
        for (int i = 0; i < 20; i++) {
            persist(userRepository, oneUserBuild().build());
        }
    }

    @Test
    public void shouldSearchUsers() throws Exception {
        setupUsers();

        // when
        ResultActions result = mock.perform(request(GET,
                        UserController.PATH + "/users").header("Authorization",
                        userToken).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        // then
        assertThat(result).isNotNull();
        assertThat(result.andExpect(jsonPath("$.length()", is(21))));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .name("User test")
                .username("teste")
                .password("123456")
                .build();

        // when
        ResultActions result = mock.perform(request(POST,
                        UserController.PATH + "/user/save").header("Authorization",
                                userToken)
                        .content(objectToJson(userDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        assertThat(result).isNotNull();
        assertThat(result.andExpect(jsonPath("$.id", notNullValue())));
        assertThat(result.andExpect(jsonPath("$.name", is(userDto.getName()))));
        assertThat(result.andExpect(jsonPath("$.username", is(userDto.getUsername()))));
        assertThat(result.andExpect(jsonPath("$.roles.length()", is(0))));
    }

    @Test
    public void shouldCreateRole() throws Exception {
        // given
        RoleDto roleDto = RoleDto.builder()
                .name("ROLE_TEST")
                .build();

        // when
        ResultActions result = mock.perform(request(POST,
                        UserController.PATH + "/role/save").header("Authorization",
                                userToken)
                        .content(objectToJson(roleDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        assertThat(result).isNotNull();
        assertThat(result.andExpect(jsonPath("$.id", notNullValue())));
        assertThat(result.andExpect(jsonPath("$.name", is(roleDto.getName()))));
    }

    @Test
    public void shouldAddRoleToUser() throws Exception {
        // given
        User user = persist(userRepository, oneUserBuild().build());
        Role role = persist(roleRepository, oneRoleBuild().build());

        RoleToUserDto roleToUserDto = RoleToUserDto.builder()
                .username(user.getUsername())
                .roleName(role.getName())
                .build();

        // when
        mock.perform(request(POST,
                        UserController.PATH + "/role/addToUser").header("Authorization",
                                userToken)
                        .content(objectToJson(roleToUserDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        User userPersisted = userRepository.findByUsername(user.getUsername());
        assertThat(userPersisted).isNotNull();
        assertThat(userPersisted.getRoles()).hasSize(1);
        assertThat(userPersisted.getRoles().stream().findFirst().get().getName()).isEqualTo(role.getName());
    }


}
