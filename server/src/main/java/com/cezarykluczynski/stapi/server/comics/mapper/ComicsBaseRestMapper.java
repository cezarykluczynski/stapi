package com.cezarykluczynski.stapi.server.comics.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBase;
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface ComicsBaseRestMapper {

	ComicsRequestDTO mapBase(ComicsRestBeanParams comicsRestBeanParams);

	ComicsBase mapBase(Comics comics);

	List<ComicsBase> mapBase(List<Comics> comicsList);

}
