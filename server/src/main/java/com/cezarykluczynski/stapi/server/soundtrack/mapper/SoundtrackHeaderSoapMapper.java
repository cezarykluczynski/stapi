package com.cezarykluczynski.stapi.server.soundtrack.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackHeader;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SoundtrackHeaderSoapMapper {

	SoundtrackHeader map(Soundtrack soundtrack);

	List<SoundtrackHeader> map(List<Soundtrack> soundtrack);

}
