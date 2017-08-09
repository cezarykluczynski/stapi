package com.cezarykluczynski.stapi.server.soundtrack.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBase;
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface SoundtrackBaseRestMapper {

	SoundtrackRequestDTO mapBase(SoundtrackRestBeanParams soundtrackRestBeanParams);

	SoundtrackBase mapBase(Soundtrack soundtrack);

	List<SoundtrackBase> mapBase(List<Soundtrack> soundtrackList);

}
