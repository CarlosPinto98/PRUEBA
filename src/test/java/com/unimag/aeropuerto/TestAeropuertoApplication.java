package com.unimag.aeropuerto;

import org.springframework.boot.SpringApplication;

public class TestAeropuertoApplication {

    public static void main(String[] args) {
        SpringApplication.from(AeropuertoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
