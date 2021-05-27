package com.demo.api.webflux.service;

import com.demo.api.webflux.dto.HeroDto;
import com.demo.api.webflux.entity.Hero;
import com.demo.api.webflux.exception.HeroNotFoundException;
import com.demo.api.webflux.repository.HeroRepository;
import mapper.HeroMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroService {
    private static final HeroMapper MAPPER = HeroMapper.INSTANCE;
    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Flux<Hero> findAll() {
        return heroRepository.findAll();
    }

    public Mono<Hero> save(HeroDto heroDto) {
        return heroRepository.save(MAPPER.toHero(heroDto));
    }

    public Mono<Hero> update(String id, HeroDto heroDto) {
        return findById(id)
            .map(hero -> {
                Hero heroFromRequest = MAPPER.toHero(heroDto);
                heroFromRequest.setId(id);
                return heroFromRequest;
            })
            .doOnSuccess(hero -> heroRepository.save(hero).subscribe());
    }

    public Mono<Hero> findById(String id) {
        return heroRepository
            .findById(id)
            .switchIfEmpty(Mono.error(new HeroNotFoundException()));
    }

    public void delete(String id) {
        heroRepository.deleteById(id).subscribe();
    }
}
