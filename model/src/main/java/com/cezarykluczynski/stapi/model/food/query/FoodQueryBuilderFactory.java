package com.cezarykluczynski.stapi.model.food.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.CachingStrategy;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FoodQueryBuilderFactory extends AbstractQueryBuilderFactory<Food> {

	@Inject
	public FoodQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Food.class);
	}

}
