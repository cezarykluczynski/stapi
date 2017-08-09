package com.cezarykluczynski.stapi.server.soundtrack.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFull;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, DateMapper.class, EnumMapper.class, StaffBaseRestMapper.class,
		ReferenceRestMapper.class})
public interface SoundtrackFullRestMapper {

	SoundtrackFull mapFull(Soundtrack soundtrack);

}
