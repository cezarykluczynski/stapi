package com.cezarykluczynski.stapi.server.literature.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFull;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest;
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface LiteratureFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "earthlyOrigin", ignore = true)
	@Mapping(target = "shakespeareanWork", ignore = true)
	@Mapping(target = "report", ignore = true)
	@Mapping(target = "scientificLiterature", ignore = true)
	@Mapping(target = "technicalManual", ignore = true)
	@Mapping(target = "religiousLiterature", ignore = true)
	@Mapping(target = "sort", ignore = true)
	LiteratureRequestDTO mapFull(LiteratureFullRequest literatureFullRequest);

	LiteratureFull mapFull(Literature literature);

}
