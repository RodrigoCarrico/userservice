package com.demo.userservice.domain;

import com.demo.userservice.domain.events.UserCreatedEvent;
import com.demo.userservice.utils.DomainCommandEvents;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User extends DomainCommandEvents {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String username;

    @NonNull
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @Builder
    public User(Long id, @NonNull String name, @NonNull String username, @NonNull String password, Collection<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        if (roles == null) {
            this.roles = new ArrayList<>();
        } else {
            this.roles = roles;
        }
        this.addEvent(UserCreatedEvent.from(this));
    }
}
