package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.rest.model.TitleBase;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2Base;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams;
import com.cezarykluczynski.stapi.server.title.dto.TitleV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface TitleBaseRestMapper {

	@Mapping(target = "educationTitle", ignore = true)
	TitleRequestDTO mapBase(TitleRestBeanParams titleRestBeanParams);

	@Mapping(target = "position", constant = "false")
	TitleBase mapBase(Title title);

	List<TitleBase> mapBase(List<Title> titleList);

	@Mapping(target = "position", ignore = true)
	TitleRequestDTO mapV2Base(TitleV2RestBeanParams titleV2RestBeanParams);

	TitleV2Base mapV2Base(Title title);

	List<TitleV2Base> mapV2Base(List<Title> titleList);

}
