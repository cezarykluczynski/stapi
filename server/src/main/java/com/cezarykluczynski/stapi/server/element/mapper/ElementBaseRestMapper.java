package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2Base;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams;
import com.cezarykluczynski.stapi.server.element.dto.ElementV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface ElementBaseRestMapper {

	@Mapping(source = "transuranium", target = "transuranic")
	ElementRequestDTO mapBase(ElementRestBeanParams elementRestBeanParams);

	@Mapping(source = "transuranic", target = "transuranium")
	ElementBase mapBase(Element element);

	List<ElementBase> mapBase(List<Element> elementList);

	ElementRequestDTO mapV2Base(ElementV2RestBeanParams elementV2RestBeanParams);

	ElementV2Base mapV2Base(Element element);

	List<ElementV2Base> mapV2Base(List<Element> elementList);

}
