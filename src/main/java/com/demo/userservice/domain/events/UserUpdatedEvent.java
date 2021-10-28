package com.demo.userservice.domain.events;

import com.demo.userservice.domain.User;
import com.demo.userservice.utils.DomainCommand;
import lombok.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Builder
@ToString
public class UserUpdatedEvent implements DomainCommand {
    public static final String NAME = "UserUpdatedEvent";

    private final Long id;
    private final String namePrincipal;
    private final String username;
    private final String password;


    public static UserUpdatedEvent from(User user) {
        return UserUpdatedEvent.builder()
                .id(user.getId())
                .username(user.getUsername())
                .namePrincipal(user.getName())
                .password(user.getPassword())
                .build();
    }


}
