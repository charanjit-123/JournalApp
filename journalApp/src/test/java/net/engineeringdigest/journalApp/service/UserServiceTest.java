package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testAdd() {
        assertEquals(4,2+2);
    }


    @Test
    @Disabled
    public void testfindByUsername() {
        User user = userRepository.findByUsername("Shubham");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void testAdd(int a,int b,int e) {
        assertEquals(e,a+b);
    }
}
