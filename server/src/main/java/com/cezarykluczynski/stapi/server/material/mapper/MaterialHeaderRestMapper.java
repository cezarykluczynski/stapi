package com.cezarykluczynski.stapi.server.material.mapper;

import com.cezarykluczynski.stapi.client.rest.model.MaterialHeader;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialHeaderRestMapper {

	MaterialHeader map(Material material);

	List<MaterialHeader> map(List<Material> material);

}
