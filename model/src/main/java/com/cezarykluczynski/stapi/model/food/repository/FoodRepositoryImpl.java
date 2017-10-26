package com.cezarykluczynski.stapi.model.food.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.food.entity.Food_;
import com.cezarykluczynski.stapi.model.food.query.FoodQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FoodRepositoryImpl implements FoodRepositoryCustom {

	private final FoodQueryBuilderFactory foodQueryBuilderFactory;

	public FoodRepositoryImpl(FoodQueryBuilderFactory foodQueryBuilderFactory) {
		this.foodQueryBuilderFactory = foodQueryBuilderFactory;
	}

	@Override
	public Page<Food> findMatching(FoodRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Food> foodQueryBuilder = foodQueryBuilderFactory.createQueryBuilder(pageable);

		foodQueryBuilder.equal(Food_.uid, criteria.getUid());
		foodQueryBuilder.like(Food_.name, criteria.getName());
		foodQueryBuilder.equal(Food_.earthlyOrigin, criteria.getEarthlyOrigin());
		foodQueryBuilder.equal(Food_.dessert, criteria.getDessert());
		foodQueryBuilder.equal(Food_.fruit, criteria.getFruit());
		foodQueryBuilder.equal(Food_.herbOrSpice, criteria.getHerbOrSpice());
		foodQueryBuilder.equal(Food_.sauce, criteria.getSauce());
		foodQueryBuilder.equal(Food_.soup, criteria.getSoup());
		foodQueryBuilder.equal(Food_.beverage, criteria.getBeverage());
		foodQueryBuilder.equal(Food_.alcoholicBeverage, criteria.getAlcoholicBeverage());
		foodQueryBuilder.equal(Food_.juice, criteria.getJuice());
		foodQueryBuilder.equal(Food_.tea, criteria.getTea());
		foodQueryBuilder.setSort(criteria.getSort());

		return foodQueryBuilder.findPage();
	}

}
