package com.cezarykluczynski.stapi.server.season.mapper;

import com.cezarykluczynski.stapi.client.rest.model.SeasonBase;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface SeasonBaseRestMapper {

	@Mapping(target = "companionSeriesSeason", ignore = true)
	SeasonRequestDTO mapBase(SeasonRestBeanParams seasonRestBeanParams);

	@AfterMapping
	default void mapBase(SeasonRestBeanParams seasonRestBeanParams, @MappingTarget SeasonRequestDTO seasonRequestDTO) {
		seasonRequestDTO.setCompanionSeriesSeason(false);
	}

	SeasonBase mapBase(Season season);

	List<SeasonBase> mapBase(List<Season> seasonList);

}
