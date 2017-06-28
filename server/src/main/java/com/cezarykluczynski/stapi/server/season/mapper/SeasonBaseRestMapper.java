package com.cezarykluczynski.stapi.server.season.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBase;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface SeasonBaseRestMapper {

	SeasonRequestDTO mapBase(SeasonRestBeanParams seasonRestBeanParams);

	SeasonBase mapBase(Season season);

	List<SeasonBase> mapBase(List<Season> seasonList);

}
