package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ElementBase;
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface ElementBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	ElementRequestDTO mapBase(ElementBaseRequest elementBaseRequest);

	ElementBase mapBase(Element element);

	List<ElementBase> mapBase(List<Element> elementList);

}
