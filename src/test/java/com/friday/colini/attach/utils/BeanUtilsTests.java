package com.friday.colini.attach.utils;

import com.friday.colini.attach.Application;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanUtilsTests {
    @Test
    public void test() {
        var application = BeanUtils.getBean(Application.class);

        Assertions.assertThat(application).isNotNull();

    }
}
