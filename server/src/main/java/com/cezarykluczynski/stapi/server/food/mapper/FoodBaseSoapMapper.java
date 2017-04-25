package com.cezarykluczynski.stapi.server.food.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.FoodBase;
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest;
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface FoodBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	FoodRequestDTO mapBase(FoodBaseRequest foodBaseRequest);

	FoodBase mapBase(Food food);

	List<FoodBase> mapBase(List<Food> foodList);

}
