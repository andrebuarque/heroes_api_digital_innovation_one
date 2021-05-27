package com.demo.api.webflux.controller.doc;

import com.demo.api.webflux.dto.HeroDto;
import com.demo.api.webflux.entity.Hero;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(description = "API responsável por gerenciar dados de super heróis da Marvel e da DC")
public interface HeroControllerDocs {
    @ApiOperation("Busca todos os heróis")
    Flux<Hero> findAll();

    @ApiOperation("Cadastra um herói")
    Mono<Hero> save(HeroDto heroDto);

    @ApiOperation("Atualiza os dados de um herói")
    Mono<Hero> update(String id, HeroDto heroDto);

    @ApiOperation("Busca um herói")
    Mono<Hero> findById(String id);

    @ApiOperation("Deleta um herói")
    void deleteById(String id);
}
