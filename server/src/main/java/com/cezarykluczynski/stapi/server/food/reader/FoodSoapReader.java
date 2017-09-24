package com.cezarykluczynski.stapi.server.food.reader;

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper;
import com.cezarykluczynski.stapi.server.food.query.FoodSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FoodSoapReader implements BaseReader<FoodBaseRequest, FoodBaseResponse>, FullReader<FoodFullRequest, FoodFullResponse> {

	private final FoodSoapQuery foodSoapQuery;

	private final FoodBaseSoapMapper foodBaseSoapMapper;

	private final FoodFullSoapMapper foodFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public FoodSoapReader(FoodSoapQuery foodSoapQuery, FoodBaseSoapMapper foodBaseSoapMapper, FoodFullSoapMapper foodFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.foodSoapQuery = foodSoapQuery;
		this.foodBaseSoapMapper = foodBaseSoapMapper;
		this.foodFullSoapMapper = foodFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public FoodBaseResponse readBase(FoodBaseRequest input) {
		Page<Food> foodPage = foodSoapQuery.query(input);
		FoodBaseResponse foodResponse = new FoodBaseResponse();
		foodResponse.setPage(pageMapper.fromPageToSoapResponsePage(foodPage));
		foodResponse.setSort(sortMapper.map(input.getSort()));
		foodResponse.getFoods().addAll(foodBaseSoapMapper.mapBase(foodPage.getContent()));
		return foodResponse;
	}

	@Override
	public FoodFullResponse readFull(FoodFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Food> foodPage = foodSoapQuery.query(input);
		FoodFullResponse foodFullResponse = new FoodFullResponse();
		foodFullResponse.setFood(foodFullSoapMapper.mapFull(Iterables.getOnlyElement(foodPage.getContent(), null)));
		return foodFullResponse;
	}

}
