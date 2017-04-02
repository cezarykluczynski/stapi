package com.cezarykluczynski.stapi.server.food.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBase;
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface FoodBaseRestMapper {

	FoodRequestDTO mapBase(FoodRestBeanParams foodRestBeanParams);

	FoodBase mapBase(Food food);

	List<FoodBase> mapBase(List<Food> foodList);

}
