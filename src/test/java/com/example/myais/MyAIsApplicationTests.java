package com.example.myais;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyAIsApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("\"123\".split(\",\").length = " + "123".split(",").length);
    }

}
