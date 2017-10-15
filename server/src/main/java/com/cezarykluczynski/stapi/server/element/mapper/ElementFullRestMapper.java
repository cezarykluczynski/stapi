package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFull;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface ElementFullRestMapper {

	ElementFull mapFull(Element element);

}
