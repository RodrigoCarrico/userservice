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
public class UserCreatedEvent implements DomainCommand {
    public static final String NAME = "UserCreatedEvent";

    private final Long id;
    private final String name;
    private final String username;
    private final String password;

    private final Collection<RolesEvent> roles;

    @Data(staticConstructor = "of")
    @EqualsAndHashCode
    public static final class RolesEvent {
        private final String id;
        private final String name;
    }

    public static UserCreatedEvent from(User user) {
        return UserCreatedEvent.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .roles(!user.getRoles().isEmpty() ? user.getRoles().stream().map(role ->
                        RolesEvent.of(role.getId().toString()
                                , role.getName())).collect(Collectors.toList()) : null).build();
    }


}
