package com.cezarykluczynski.stapi.server.material.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialFull;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest;
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface MaterialFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "chemicalCompound", ignore = true)
	@Mapping(target = "biochemicalCompound", ignore = true)
	@Mapping(target = "drug", ignore = true)
	@Mapping(target = "poisonousSubstance", ignore = true)
	@Mapping(target = "explosive", ignore = true)
	@Mapping(target = "gemstone", ignore = true)
	@Mapping(target = "alloyOrComposite", ignore = true)
	@Mapping(target = "fuel", ignore = true)
	@Mapping(target = "mineral", ignore = true)
	@Mapping(target = "preciousMaterial", ignore = true)
	@Mapping(target = "sort", ignore = true)
	MaterialRequestDTO mapFull(MaterialFullRequest materialFullRequest);

	MaterialFull mapFull(Material material);

}
