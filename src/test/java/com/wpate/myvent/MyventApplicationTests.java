package com.wpate.myvent;

import com.wpate.myvent.configuration.H2TestProfileJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyventApplication.class, H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class MyventApplicationTests {

    @Test
    public void contextLoads() {
    }

}

