package com.cezarykluczynski.stapi.model.food.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class FoodQueryBuilderFactory extends AbstractQueryBuilderFactory<Food> {

	public FoodQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Food.class);
	}

}
