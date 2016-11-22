package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerHeader;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformerHeaderSoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.PerformerHeader map(Performer performer);

	List<PerformerHeader> map(List<Performer> performer);

}
