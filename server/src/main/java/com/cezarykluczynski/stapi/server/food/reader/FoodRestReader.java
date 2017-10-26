package com.cezarykluczynski.stapi.server.food.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
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

@Service
public class FoodRestReader implements BaseReader<FoodRestBeanParams, FoodBaseResponse>, FullReader<String, FoodFullResponse> {

	private FoodRestQuery foodRestQuery;

	private FoodBaseRestMapper foodBaseRestMapper;

	private FoodFullRestMapper foodFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public FoodRestReader(FoodRestQuery foodRestQuery, FoodBaseRestMapper foodBaseRestMapper, FoodFullRestMapper foodFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.foodRestQuery = foodRestQuery;
		this.foodBaseRestMapper = foodBaseRestMapper;
		this.foodFullRestMapper = foodFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public FoodBaseResponse readBase(FoodRestBeanParams input) {
		Page<Food> foodPage = foodRestQuery.query(input);
		FoodBaseResponse foodResponse = new FoodBaseResponse();
		foodResponse.setPage(pageMapper.fromPageToRestResponsePage(foodPage));
		foodResponse.setSort(sortMapper.map(input.getSort()));
		foodResponse.getFoods().addAll(foodBaseRestMapper.mapBase(foodPage.getContent()));
		return foodResponse;
	}

	@Override
	public FoodFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		FoodRestBeanParams foodRestBeanParams = new FoodRestBeanParams();
		foodRestBeanParams.setUid(uid);
		Page<Food> foodPage = foodRestQuery.query(foodRestBeanParams);
		FoodFullResponse foodResponse = new FoodFullResponse();
		foodResponse.setFood(foodFullRestMapper.mapFull(Iterables.getOnlyElement(foodPage.getContent(), null)));
		return foodResponse;
	}
}
