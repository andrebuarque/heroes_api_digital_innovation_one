package com.demo.api.webflux.controller;

import com.demo.api.webflux.controller.doc.HeroControllerDocs;
import com.demo.api.webflux.dto.HeroDto;
import com.demo.api.webflux.entity.Hero;
import com.demo.api.webflux.service.HeroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/heroes")
public class HeroController implements HeroControllerDocs {
    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping
    public Flux<Hero> findAll() {
        return heroService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Hero> save(@RequestBody @Valid HeroDto heroDto) {
        return heroService.save(heroDto);
    }

    @PutMapping("{id}")
    public Mono<Hero> update(@PathVariable String id, @RequestBody @Valid HeroDto heroDto) {
        return heroService.update(id, heroDto);
    }

    @GetMapping("/{id}")
    public Mono<Hero> findById(@PathVariable String id) {
        return heroService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        heroService.delete(id);
    }
}
