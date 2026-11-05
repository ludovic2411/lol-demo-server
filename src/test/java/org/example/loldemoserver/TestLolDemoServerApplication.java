package org.example.loldemoserver;

import org.springframework.boot.SpringApplication;

public class TestLolDemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(LolDemoServerApplication::main).with(TestContainersPostgresConfiguration.class).run(args);
    }

}
