package com.cezarykluczynski.stapi.server.astronomical_object.mapper;

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectBase;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Base;
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderRestMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface AstronomicalObjectBaseRestMapper {

	AstronomicalObjectRequestDTO mapBase(AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams);

	AstronomicalObjectBase mapBase(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectBase> mapBase(List<AstronomicalObject> astronomicalObjectList);

	AstronomicalObjectV2Base mapV2Base(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectV2Base> mapV2Base(List<AstronomicalObject> astronomicalObjectList);

}
