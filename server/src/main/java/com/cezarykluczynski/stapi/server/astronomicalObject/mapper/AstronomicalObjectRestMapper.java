package com.cezarykluczynski.stapi.server.astronomicalObject.mapper;

import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderRestMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface AstronomicalObjectRestMapper {

	AstronomicalObjectRequestDTO map(AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams);

	@Mappings({
			@Mapping(target = "locationHeader", source = "location")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObject map(AstronomicalObject astronomicalObject);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObject> map(List<AstronomicalObject> astronomicalObjectList);

}
