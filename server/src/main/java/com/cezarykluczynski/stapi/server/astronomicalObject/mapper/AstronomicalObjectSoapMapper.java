package com.cezarykluczynski.stapi.server.astronomicalObject.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest;
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderSoapMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface AstronomicalObjectSoapMapper {

	AstronomicalObjectRequestDTO map(AstronomicalObjectRequest astronomicalObjectRequest);

	@Mappings({
			@Mapping(target = "locationHeader", source = "location")
	})
	com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObject map(AstronomicalObject astronomicalObject);

	List<com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObject> map(List<AstronomicalObject> astronomicalObjectList);

}
