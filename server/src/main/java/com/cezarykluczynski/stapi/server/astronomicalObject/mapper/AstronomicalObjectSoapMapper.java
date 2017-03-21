package com.cezarykluczynski.stapi.server.astronomicalObject.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
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

	@Mappings({
			@Mapping(target = "guid", ignore = true)
	})
	AstronomicalObjectRequestDTO mapBase(AstronomicalObjectBaseRequest astronomicalObjectBaseRequest);

	@Mappings({
			@Mapping(target = "locationHeader", source = "location")
	})
	AstronomicalObjectBase mapBase(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectBase> mapBase(List<AstronomicalObject> astronomicalObjectList);

	@Mappings({
			@Mapping(target = "name", ignore = true),
			@Mapping(target = "astronomicalObjectType", ignore = true),
			@Mapping(target = "locationGuid", ignore = true),
			@Mapping(target = "sort", ignore = true)
	})
	AstronomicalObjectRequestDTO mapFull(AstronomicalObjectFullRequest astronomicalObjectFullRequest);

	AstronomicalObjectFull mapFull(AstronomicalObject astronomicalObject);

}
