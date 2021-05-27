package com.demo.api.webflux.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(basePackages = "com.demo.api.webflux.repository")
@Configuration
public class MongoConfig {

}
