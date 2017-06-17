package com.cezarykluczynski.stapi.server.literature.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBase;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest;
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface LiteratureBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	LiteratureRequestDTO mapBase(LiteratureBaseRequest literatureBaseRequest);

	LiteratureBase mapBase(Literature literature);

	List<LiteratureBase> mapBase(List<Literature> literatureList);

}
