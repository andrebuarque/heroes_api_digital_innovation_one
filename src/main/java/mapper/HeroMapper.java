package mapper;

import com.demo.api.webflux.dto.HeroDto;
import com.demo.api.webflux.entity.Hero;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HeroMapper {
    HeroMapper INSTANCE = Mappers.getMapper(HeroMapper.class);

    Hero toHero(HeroDto heroDto);
}
