package com.cezarykluczynski.stapi.server.astronomical_object.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectHeader;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AstronomicalObjectHeaderRestMapper {

	AstronomicalObjectHeader map(AstronomicalObject astronomicalObject);

	List<AstronomicalObjectHeader> map(List<AstronomicalObject> astronomicalObject);

}
