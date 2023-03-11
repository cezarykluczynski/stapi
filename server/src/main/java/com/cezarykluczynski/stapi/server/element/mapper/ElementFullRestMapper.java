package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.rest.model.ElementFull;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2Full;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class)
public interface ElementFullRestMapper {

	@Mapping(source = "transuranic", target = "transuranium")
	ElementFull mapFull(Element element);

	ElementV2Full mapV2Full(Element element);

}
