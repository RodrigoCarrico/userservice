package com.demo.userservice.utils;

import com.demo.userservice.repository.RoleRepository;
import com.demo.userservice.repository.UserRepository;
import com.demo.userservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@Transactional
public abstract class AdapterConfigIT {

    @Autowired
    protected BCryptPasswordEncoder sPyBCryptPasswordEncoder;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @SpyBean
    protected UserServiceImpl userService;
}
