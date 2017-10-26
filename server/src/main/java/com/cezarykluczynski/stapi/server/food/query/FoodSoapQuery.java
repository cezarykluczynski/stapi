package com.cezarykluczynski.stapi.server.food.query;

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest;
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FoodSoapQuery {

	private final FoodBaseSoapMapper foodBaseSoapMapper;

	private final FoodFullSoapMapper foodFullSoapMapper;

	private final PageMapper pageMapper;

	private final FoodRepository foodRepository;

	public FoodSoapQuery(FoodBaseSoapMapper foodBaseSoapMapper, FoodFullSoapMapper foodFullSoapMapper, PageMapper pageMapper,
			FoodRepository foodRepository) {
		this.foodBaseSoapMapper = foodBaseSoapMapper;
		this.foodFullSoapMapper = foodFullSoapMapper;
		this.pageMapper = pageMapper;
		this.foodRepository = foodRepository;
	}

	public Page<Food> query(FoodBaseRequest foodBaseRequest) {
		FoodRequestDTO foodRequestDTO = foodBaseSoapMapper.mapBase(foodBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(foodBaseRequest.getPage());
		return foodRepository.findMatching(foodRequestDTO, pageRequest);
	}

	public Page<Food> query(FoodFullRequest foodFullRequest) {
		FoodRequestDTO seriesRequestDTO = foodFullSoapMapper.mapFull(foodFullRequest);
		return foodRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
