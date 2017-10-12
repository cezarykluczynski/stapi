package com.cezarykluczynski.stapi.server.conflict.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictHeader;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConflictHeaderSoapMapper {

	ConflictHeader map(Conflict conflict);

	List<ConflictHeader> map(List<Conflict> conflict);

}
