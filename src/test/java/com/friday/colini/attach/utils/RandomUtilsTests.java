package com.friday.colini.attach.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomUtilsTests {
    @Autowired
    private RandomUtils randomUtils;

    @Test(expected = IllegalArgumentException.class)
    public void getSecureStringFailTest() {
        randomUtils.getSecureString(0);
    }
    
    @Test
    public void getSecureStringTest() {
        final var set = new HashSet<String>();

        for (var i = 0; i < 1000; i++) {
            for (var length = 20; length <= 50; length++) {
                final var secureString = randomUtils.getSecureString(length);

                Assertions.assertThat(secureString.length()).isEqualTo(length);
                Assertions.assertThat(set.contains(secureString)).isFalse();

                set.add(secureString);
            }
        }
    }

    @Test
    public void getTimebaseUUID() {
        final var set = new HashSet<String>();

        for (var i = 0; i < 10000; i++) {
            final var secureString = randomUtils.getTimebaseUUID();

            Assertions.assertThat(set.contains(secureString)).isFalse();

            set.add(secureString);
        }
    }
}
