package com.demo.api.webflux.repository;

import com.demo.api.webflux.entity.Hero;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends ReactiveCrudRepository<Hero, String> {
}
