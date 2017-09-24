package com.cezarykluczynski.stapi.server.material.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBase;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest;
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface MaterialBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	MaterialRequestDTO mapBase(MaterialBaseRequest materialBaseRequest);

	MaterialBase mapBase(Material material);

	List<MaterialBase> mapBase(List<Material> materialList);

}
