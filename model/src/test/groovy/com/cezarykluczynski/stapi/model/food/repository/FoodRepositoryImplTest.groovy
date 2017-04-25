package com.cezarykluczynski.stapi.model.food.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.model.food.entity.Food_
import com.cezarykluczynski.stapi.model.food.query.FoodQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractFoodTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class FoodRepositoryImplTest extends AbstractFoodTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private FoodQueryBuilderFactory foodQueryBuilderFactory

	private FoodRepositoryImpl foodRepositoryImpl

	private QueryBuilder<Food> foodQueryBuilder

	private Pageable pageable

	private FoodRequestDTO foodRequestDTO

	private Food food

	private Page page

	void setup() {
		foodQueryBuilderFactory = Mock()
		foodRepositoryImpl = new FoodRepositoryImpl(foodQueryBuilderFactory)
		foodQueryBuilder = Mock()
		pageable = Mock()
		foodRequestDTO = Mock()
		page = Mock()
		food = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = foodRepositoryImpl.findMatching(foodRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * foodQueryBuilderFactory.createQueryBuilder(pageable) >> foodQueryBuilder

		then: 'uid criteria is set'
		1 * foodRequestDTO.uid >> UID
		1 * foodQueryBuilder.equal(Food_.uid, UID)

		then: 'string criteria are set'
		1 * foodRequestDTO.name >> NAME
		1 * foodQueryBuilder.like(Food_.name, NAME)

		then: 'boolean criteria are set'
		1 * foodRequestDTO.earthlyOrigin >> EARTHLY_ORIGIN
		1 * foodQueryBuilder.equal(Food_.earthlyOrigin, EARTHLY_ORIGIN)
		1 * foodRequestDTO.dessert >> DESSERT
		1 * foodQueryBuilder.equal(Food_.dessert, DESSERT)
		1 * foodRequestDTO.fruit >> FRUIT
		1 * foodQueryBuilder.equal(Food_.fruit, FRUIT)
		1 * foodRequestDTO.herbOrSpice >> HERB_OR_SPICE
		1 * foodQueryBuilder.equal(Food_.herbOrSpice, HERB_OR_SPICE)
		1 * foodRequestDTO.sauce >> SAUCE
		1 * foodQueryBuilder.equal(Food_.sauce, SAUCE)
		1 * foodRequestDTO.soup >> SOUP
		1 * foodQueryBuilder.equal(Food_.soup, SOUP)
		1 * foodRequestDTO.beverage >> BEVERAGE
		1 * foodQueryBuilder.equal(Food_.beverage, BEVERAGE)
		1 * foodRequestDTO.alcoholicBeverage >> ALCOHOLIC_BEVERAGE
		1 * foodQueryBuilder.equal(Food_.alcoholicBeverage, ALCOHOLIC_BEVERAGE)
		1 * foodRequestDTO.juice >> JUICE
		1 * foodQueryBuilder.equal(Food_.juice, JUICE)
		1 * foodRequestDTO.tea >> TEA
		1 * foodQueryBuilder.equal(Food_.tea, TEA)

		then: 'sort is set'
		1 * foodRequestDTO.sort >> SORT
		1 * foodQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * foodQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
