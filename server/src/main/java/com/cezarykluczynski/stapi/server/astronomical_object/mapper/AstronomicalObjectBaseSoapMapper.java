package com.cezarykluczynski.stapi.server.astronomical_object.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderSoapMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface AstronomicalObjectBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	AstronomicalObjectRequestDTO mapBase(AstronomicalObjectBaseRequest astronomicalObjectBaseRequest);

	AstronomicalObjectBase mapBase(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectBase> mapBase(List<AstronomicalObject> astronomicalObjectList);

}
