package com.demo.api.webflux.controller;

import com.demo.api.webflux.dto.HeroDto;
import com.demo.api.webflux.entity.Hero;
import com.demo.api.webflux.exception.HeroNotFoundException;
import com.demo.api.webflux.service.HeroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(HeroController.class)
class HeroControllerTest {
    static final String BASE_URL = "/heroes";

    @MockBean
    HeroService heroService;

    @Autowired
    WebTestClient webClient;

    @Test
    void testFindAll() {
        final Hero hero = getHero();
        final Hero hero1 = new Hero();
        hero.setId("321");
        hero.setName("name 2");
        hero.setUniverse("universe 2");
        hero.setFilms(2);

        when(heroService.findAll()).thenReturn(Flux.just(hero, hero1));

        webClient.get()
            .uri(BASE_URL)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray();
    }

    @Test
    void testSaveWithoutRequiredFields() {
        webClient.post()
            .uri(BASE_URL)
            .bodyValue(new Hero())
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void testSave() {
        final Hero body = getHero();

        webClient.post()
            .uri(BASE_URL)
            .bodyValue(body)
            .exchange()
            .expectStatus().isCreated();
    }

    @Test
    void testUpdateWithoutRequiredFields() {
        webClient.put()
            .uri(BASE_URL + "/1")
            .bodyValue(new Hero())
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateWithUnknownId() {
        when(heroService.update(anyString(), any(HeroDto.class)))
            .thenReturn(Mono.error(new HeroNotFoundException()));

        webClient.put()
            .uri(BASE_URL + "/1")
            .bodyValue(getHero())
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testUpdate() {
        final Hero hero = new Hero();
        hero.setId("123");
        hero.setName("name");
        hero.setFilms(19);
        hero.setUniverse("old universe");

        when(heroService.update(anyString(), any(HeroDto.class)))
            .thenReturn(Mono.just(hero));

        webClient.put()
            .uri(BASE_URL + "/123")
            .bodyValue(getHero())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(hero.getId())
            .jsonPath("$.name").isEqualTo(hero.getName())
            .jsonPath("$.films").isEqualTo(hero.getFilms())
            .jsonPath("$.universe").isEqualTo(hero.getUniverse());
    }

    @Test
    void testFindByIdWithUnknownId() {
        when(heroService.findById(anyString()))
            .thenReturn(Mono.error(new HeroNotFoundException()));

        webClient.get()
            .uri(BASE_URL + "/123")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testFindById() {
        final Hero hero = getHero();

        when(heroService.findById(anyString()))
            .thenReturn(Mono.just(hero));

        webClient.get()
            .uri(BASE_URL + "/123")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(hero.getId())
            .jsonPath("$.name").isEqualTo(hero.getName())
            .jsonPath("$.films").isEqualTo(hero.getFilms())
            .jsonPath("$.universe").isEqualTo(hero.getUniverse());
    }

    @Test
    void testDeleteById() {
        webClient.delete()
            .uri(BASE_URL + "/123")
            .exchange()
            .expectStatus().isNoContent();
    }

    private Hero getHero() {
        final Hero hero = new Hero();
        hero.setId("123");
        hero.setName("name 1");
        hero.setUniverse("universe 1");
        hero.setFilms(1);
        return hero;
    }
}