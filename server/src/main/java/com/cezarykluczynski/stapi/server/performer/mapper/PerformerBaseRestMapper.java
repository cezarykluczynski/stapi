package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerV2Base;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface PerformerBaseRestMapper {

	@Mapping(target = "audiobookPerformer", ignore = true)
	@Mapping(target = "cutPerformer", ignore = true)
	@Mapping(target = "ldPerformer", ignore = true)
	@Mapping(target = "picPerformer", ignore = true)
	@Mapping(target = "proPerformer", ignore = true)
	@Mapping(target = "puppeteer", ignore = true)
	@Mapping(target = "snwPerformer", ignore = true)
	@Mapping(target = "stPerformer", ignore = true)
	PerformerRequestDTO mapBase(PerformerRestBeanParams performerRestBeanParams);

	PerformerRequestDTO mapBase(PerformerV2RestBeanParams performerV2RestBeanParams);

	PerformerBase mapBase(Performer performer);

	List<PerformerBase> mapBase(List<Performer> performerList);

	List<PerformerV2Base> mapV2Base(List<Performer> performerList);

}
