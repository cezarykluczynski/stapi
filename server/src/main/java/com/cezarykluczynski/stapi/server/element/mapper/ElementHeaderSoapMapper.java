package com.cezarykluczynski.stapi.server.element.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ElementHeader;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ElementHeaderSoapMapper {

	ElementHeader map(Element element);

	List<ElementHeader> map(List<Element> element);

}
