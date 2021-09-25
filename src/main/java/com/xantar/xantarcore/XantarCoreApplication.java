package com.xantar.xantarcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.xantar.xantarcore.db, com.xantar.xantarcore.meals, com.xantar.xantarcore.rest")
public class XantarCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(XantarCoreApplication.class, args);
	}

}
