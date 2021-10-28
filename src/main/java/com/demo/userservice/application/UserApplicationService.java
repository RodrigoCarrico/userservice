package com.demo.userservice.application;

import com.demo.userservice.amqp.Publisher;
import com.demo.userservice.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserApplicationService {

    private final Publisher publisher;

    public void handle() {
        var user = User.builder()
                .id(1L)
                .username("teste")
                .name("teste name")
                .password("123456")
                .build();

        user.getEvents().forEach(publisher::dispatch);

    }


}
