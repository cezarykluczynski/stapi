package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBase;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface PerformerBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "dateOfBirth.from", target = "dateOfBirthFrom")
	@Mapping(source = "dateOfBirth.to", target = "dateOfBirthTo")
	@Mapping(source = "dateOfDeath.from", target = "dateOfDeathFrom")
	@Mapping(source = "dateOfDeath.to", target = "dateOfDeathTo")
	PerformerRequestDTO mapBase(PerformerBaseRequest performerBaseRequest);

	PerformerBase mapBase(Performer performer);

	List<PerformerBase> mapBase(List<Performer> performerList);

}
