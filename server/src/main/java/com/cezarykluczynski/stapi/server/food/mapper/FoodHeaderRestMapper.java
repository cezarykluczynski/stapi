package com.cezarykluczynski.stapi.server.food.mapper;

import com.cezarykluczynski.stapi.client.rest.model.FoodHeader;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodHeaderRestMapper {

	FoodHeader map(Food food);

	List<FoodHeader> map(List<Food> food);

}
