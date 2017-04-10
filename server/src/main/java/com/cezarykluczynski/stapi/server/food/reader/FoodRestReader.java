package com.cezarykluczynski.stapi.server.food.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper;
import com.cezarykluczynski.stapi.server.food.query.FoodRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FoodRestReader implements BaseReader<FoodRestBeanParams, FoodBaseResponse>,
		FullReader<String, FoodFullResponse> {

	private FoodRestQuery foodRestQuery;

	private FoodBaseRestMapper foodBaseRestMapper;

	private FoodFullRestMapper foodFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public FoodRestReader(FoodRestQuery foodRestQuery, FoodBaseRestMapper foodBaseRestMapper,
			FoodFullRestMapper foodFullRestMapper, PageMapper pageMapper) {
		this.foodRestQuery = foodRestQuery;
		this.foodBaseRestMapper = foodBaseRestMapper;
		this.foodFullRestMapper = foodFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public FoodBaseResponse readBase(FoodRestBeanParams input) {
		Page<Food> foodPage = foodRestQuery.query(input);
		FoodBaseResponse foodResponse = new FoodBaseResponse();
		foodResponse.setPage(pageMapper.fromPageToRestResponsePage(foodPage));
		foodResponse.getFoods().addAll(foodBaseRestMapper.mapBase(foodPage.getContent()));
		return foodResponse;
	}

	@Override
	public FoodFullResponse readFull(String guid) {
		StaticValidator.requireGuid(guid);
		FoodRestBeanParams foodRestBeanParams = new FoodRestBeanParams();
		foodRestBeanParams.setGuid(guid);
		Page<com.cezarykluczynski.stapi.model.food.entity.Food> foodPage = foodRestQuery
				.query(foodRestBeanParams);
		FoodFullResponse foodResponse = new FoodFullResponse();
		foodResponse.setFood(foodFullRestMapper
				.mapFull(Iterables.getOnlyElement(foodPage.getContent(), null)));
		return foodResponse;
	}
}
