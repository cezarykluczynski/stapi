package com.cezarykluczynski.stapi.server.literature.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureHeader;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LiteratureHeaderRestMapper {

	LiteratureHeader map(Literature literature);

	List<LiteratureHeader> map(List<Literature> literature);

}
