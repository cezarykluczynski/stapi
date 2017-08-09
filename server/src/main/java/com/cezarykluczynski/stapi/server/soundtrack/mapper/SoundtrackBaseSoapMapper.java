package com.cezarykluczynski.stapi.server.soundtrack.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBase;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest;
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface SoundtrackBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "releaseDate.from", target = "releaseDateFrom")
	@Mapping(source = "releaseDate.to", target = "releaseDateTo")
	@Mapping(source = "length.from", target = "lengthFrom")
	@Mapping(source = "length.to", target = "lengthTo")
	SoundtrackRequestDTO mapBase(SoundtrackBaseRequest soundtrackBaseRequest);

	SoundtrackBase mapBase(Soundtrack soundtrack);

	List<SoundtrackBase> mapBase(List<Soundtrack> soundtrackList);

}
