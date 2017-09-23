package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBase;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface TitleBaseRestMapper {

	TitleRequestDTO mapBase(TitleRestBeanParams titleRestBeanParams);

	TitleBase mapBase(Title title);

	List<TitleBase> mapBase(List<Title> titleList);

}
