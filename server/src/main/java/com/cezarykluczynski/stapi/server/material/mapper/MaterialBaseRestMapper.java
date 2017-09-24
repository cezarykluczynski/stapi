package com.cezarykluczynski.stapi.server.material.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBase;
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface MaterialBaseRestMapper {

	MaterialRequestDTO mapBase(MaterialRestBeanParams materialRestBeanParams);

	MaterialBase mapBase(Material material);

	List<MaterialBase> mapBase(List<Material> materialList);

}
