package com.cezarykluczynski.stapi.server.food.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFull;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FoodFullRestMapper {

	FoodFull mapFull(Food food);

}
