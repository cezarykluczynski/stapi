package com.cezarykluczynski.stapi.server.astronomicalObject.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderSoapMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface AstronomicalObjectBaseSoapMapper {

	@Mapping(target = "guid", ignore = true)
	AstronomicalObjectRequestDTO mapBase(AstronomicalObjectBaseRequest astronomicalObjectBaseRequest);

	AstronomicalObjectBase mapBase(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectBase> mapBase(List<AstronomicalObject> astronomicalObjectList);

}
