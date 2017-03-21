package com.cezarykluczynski.stapi.server.astronomicalObject.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFull;
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

	AstronomicalObjectRequestDTO mapBase(AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams);

	@Mappings({
			@Mapping(target = "locationHeader", source = "location")
	})
	AstronomicalObjectBase mapBase(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectBase> mapBase(List<AstronomicalObject> astronomicalObjectList);

	AstronomicalObjectFull mapFull(AstronomicalObject astronomicalObject);

}
