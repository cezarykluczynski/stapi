package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TitleBase;
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface TitleBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	TitleRequestDTO mapBase(TitleBaseRequest titleBaseRequest);

	TitleBase mapBase(Title title);

	List<TitleBase> mapBase(List<Title> titleList);

}
