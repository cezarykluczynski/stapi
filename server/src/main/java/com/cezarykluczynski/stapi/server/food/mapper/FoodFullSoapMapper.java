package com.cezarykluczynski.stapi.server.food.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.FoodFull;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest;
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface FoodFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "earthlyOrigin", ignore = true)
	@Mapping(target = "dessert", ignore = true)
	@Mapping(target = "fruit", ignore = true)
	@Mapping(target = "herbOrSpice", ignore = true)
	@Mapping(target = "sauce", ignore = true)
	@Mapping(target = "soup", ignore = true)
	@Mapping(target = "beverage", ignore = true)
	@Mapping(target = "alcoholicBeverage", ignore = true)
	@Mapping(target = "juice", ignore = true)
	@Mapping(target = "tea", ignore = true)
	@Mapping(target = "sort", ignore = true)
	FoodRequestDTO mapFull(FoodFullRequest foodFullRequest);

	FoodFull mapFull(Food food);

}
