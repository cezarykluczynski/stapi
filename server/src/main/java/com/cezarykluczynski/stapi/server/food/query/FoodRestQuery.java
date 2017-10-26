package com.cezarykluczynski.stapi.server.food.query;

import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FoodRestQuery {

	private final FoodBaseRestMapper foodBaseRestMapper;

	private final PageMapper pageMapper;

	private final FoodRepository foodRepository;

	public FoodRestQuery(FoodBaseRestMapper foodBaseRestMapper, PageMapper pageMapper, FoodRepository foodRepository) {
		this.foodBaseRestMapper = foodBaseRestMapper;
		this.pageMapper = pageMapper;
		this.foodRepository = foodRepository;
	}

	public Page<Food> query(FoodRestBeanParams foodRestBeanParams) {
		FoodRequestDTO foodRequestDTO = foodBaseRestMapper.mapBase(foodRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(foodRestBeanParams);
		return foodRepository.findMatching(foodRequestDTO, pageRequest);
	}

}
