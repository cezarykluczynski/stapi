package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBase;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface SpacecraftClassBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	SpacecraftClassRequestDTO mapBase(SpacecraftClassBaseRequest spacecraftClassBaseRequest);

	SpacecraftClassBase mapBase(SpacecraftClass spacecraftClass);

	List<SpacecraftClassBase> mapBase(List<SpacecraftClass> spacecraftClassList);

}
