package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TitleHeader;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TitleHeaderSoapMapper {

	TitleHeader map(Title title);

	List<TitleHeader> map(List<Title> title);

}
