package com.demo.api.webflux.service;

import com.demo.api.webflux.dto.HeroDto;
import com.demo.api.webflux.entity.Hero;
import com.demo.api.webflux.exception.HeroNotFoundException;
import com.demo.api.webflux.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {
    @InjectMocks
    HeroService heroService;

    @Mock
    HeroRepository heroRepository;

    @Captor
    ArgumentCaptor<Hero> heroArgumentCaptor;

    @Test
    void testFindAll() {
        Hero hero1 = new Hero();
        Hero hero2 = new Hero();

        when(heroRepository.findAll()).thenReturn(Flux.just(hero1, hero2));

        final Flux<Hero> heroes = heroService.findAll();

        StepVerifier
            .create(heroes)
            .expectNext(hero1, hero2)
            .expectComplete()
            .verify();
    }

    @Test
    void testFindAllEmpty() {
        when(heroRepository.findAll()).thenReturn(Flux.empty());

        final Flux<Hero> heroes = heroService.findAll();

        StepVerifier
            .create(heroes)
            .expectNextCount(0)
            .expectComplete()
            .verify();
    }

    @Test
    void testFindById() {
        Hero hero = new Hero();

        when(heroRepository.findById(anyString())).thenReturn(Mono.just(hero));

        final Mono<Hero> foundHero = heroService.findById("id");

        StepVerifier
            .create(foundHero)
            .expectNext(hero)
            .expectComplete()
            .verify();
    }

    @Test
    void testFindByIdEmpty() {
        when(heroRepository.findById(anyString())).thenReturn(Mono.empty());

        final Mono<Hero> foundHero = heroService.findById("id");

        StepVerifier
            .create(foundHero)
            .expectError(HeroNotFoundException.class)
            .verify();
    }

    @Test
    void testSave() {
        Hero hero = new Hero();
        when(heroRepository.save(any(Hero.class))).thenReturn(Mono.just(hero));

        HeroDto heroDto = new HeroDto();
        heroDto.setFilms(2);
        heroDto.setName("hero name");
        heroDto.setUniverse("hero universe");
        final Mono<Hero> savedHero = heroService.save(heroDto);

        verify(heroRepository).save(heroArgumentCaptor.capture());

        StepVerifier
            .create(savedHero)
            .expectNext(hero)
            .expectComplete()
            .verify();

        final Hero argumentCaptorValue = heroArgumentCaptor.getValue();
        assertThat(argumentCaptorValue.getFilms()).isEqualTo(heroDto.getFilms());
        assertThat(argumentCaptorValue.getName()).isEqualTo(heroDto.getName());
        assertThat(argumentCaptorValue.getUniverse()).isEqualTo(heroDto.getUniverse());
    }

    @Test
    void testUpdate() {
        String id = "id";
        Hero hero = new Hero();

        when(heroRepository.findById(anyString())).thenReturn(Mono.just(hero));
        when(heroRepository.save(any(Hero.class))).thenReturn(Mono.just(hero));

        final Mono<Hero> updatedHero = heroService.update(id, new HeroDto());

        StepVerifier
            .create(updatedHero)
            .expectNextMatches(h -> h.getId().equals(id))
            .expectComplete()
            .verify();
    }

    @Test
    void testUpdateEmpty() {
        when(heroRepository.findById(anyString())).thenReturn(Mono.empty());

        final Mono<Hero> updatedHero = heroService.update("", new HeroDto());

        StepVerifier
            .create(updatedHero)
            .expectError(HeroNotFoundException.class)
            .verify();
    }

    @Test
    void testDelete() {
        final String id = "id";

        when(heroRepository.deleteById(anyString())).thenReturn(Mono.empty());

        heroService.delete(id);

        verify(heroRepository).deleteById(eq(id));
    }
}