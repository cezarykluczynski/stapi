package com.cezarykluczynski.stapi.server.conflict.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBase;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest;
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {OrganizationHeaderSoapMapper.class, RequestSortSoapMapper.class,
		SpeciesHeaderSoapMapper.class})
public interface ConflictBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	ConflictRequestDTO mapBase(ConflictBaseRequest conflictBaseRequest);

	ConflictBase mapBase(Conflict conflict);

	List<ConflictBase> mapBase(List<Conflict> conflictList);

}
