package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn((net.engineeringdigest.journalApp.entity.User) User.builder().username("ram").password("inrinrick").roles(String.valueOf(new ArrayList<>())).build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}

