package com.demo.userservice.application;

import com.demo.userservice.utils.AdapterConfigIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User Application Service test")
public class UserApplicationServiceTest extends AdapterConfigIT {

    @Test
    public void shouldBeTestHandle() {
        userApplicationService.handle();
        System.out.println("Example");
    }


}
