package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBase;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface ElementBaseRestMapper {

	ElementRequestDTO mapBase(ElementRestBeanParams elementRestBeanParams);

	ElementBase mapBase(Element element);

	List<ElementBase> mapBase(List<Element> elementList);

}
