package com.hateoas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BookApiApplication.class)
@Transactional
@WebAppConfiguration
public class BookApiApplicationTests {

    @Test
    public void contextLoads() {
    }

}
