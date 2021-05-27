package com.demo.api.webflux;

import com.demo.api.webflux.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebfluxApplication {
	@Autowired
	HeroService heroService;

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

}
