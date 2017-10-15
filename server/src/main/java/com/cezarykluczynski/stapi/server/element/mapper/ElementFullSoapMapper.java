package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ElementFull;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface ElementFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "symbol", ignore = true)
	@Mapping(target = "transuranium", ignore = true)
	@Mapping(target = "gammaSeries", ignore = true)
	@Mapping(target = "hypersonicSeries", ignore = true)
	@Mapping(target = "megaSeries", ignore = true)
	@Mapping(target = "omegaSeries", ignore = true)
	@Mapping(target = "transonicSeries", ignore = true)
	@Mapping(target = "worldSeries", ignore = true)
	@Mapping(target = "sort", ignore = true)
	ElementRequestDTO mapFull(ElementFullRequest elementFullRequest);

	ElementFull mapFull(Element element);

}
