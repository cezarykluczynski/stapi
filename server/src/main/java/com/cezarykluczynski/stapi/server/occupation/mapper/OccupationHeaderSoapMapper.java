package com.cezarykluczynski.stapi.server.occupation.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationHeader;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OccupationHeaderSoapMapper {

	OccupationHeader map(Occupation occupation);

	List<OccupationHeader> map(List<Occupation> occupation);

}
