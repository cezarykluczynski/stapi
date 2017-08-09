package com.cezarykluczynski.stapi.server.soundtrack.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFull;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest;
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseSoapMapper.class, DateMapper.class, EnumMapper.class, StaffBaseSoapMapper.class,
		ReferenceSoapMapper.class})
public interface SoundtrackFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "releaseDateFrom", ignore = true)
	@Mapping(target = "releaseDateTo", ignore = true)
	@Mapping(target = "lengthFrom", ignore = true)
	@Mapping(target = "lengthTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	SoundtrackRequestDTO mapFull(SoundtrackFullRequest soundtrackFullRequest);

	SoundtrackFull mapFull(Soundtrack soundtrack);

}
