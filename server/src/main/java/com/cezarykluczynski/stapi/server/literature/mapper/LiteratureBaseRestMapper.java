package com.cezarykluczynski.stapi.server.literature.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBase;
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface LiteratureBaseRestMapper {

	LiteratureRequestDTO mapBase(LiteratureRestBeanParams literatureRestBeanParams);

	LiteratureBase mapBase(Literature literature);

	List<LiteratureBase> mapBase(List<Literature> literatureList);

}
