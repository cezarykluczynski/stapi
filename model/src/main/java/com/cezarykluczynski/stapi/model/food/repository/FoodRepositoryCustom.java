package com.cezarykluczynski.stapi.model.food.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;

public interface FoodRepositoryCustom extends CriteriaMatcher<FoodRequestDTO, Food> {
}
