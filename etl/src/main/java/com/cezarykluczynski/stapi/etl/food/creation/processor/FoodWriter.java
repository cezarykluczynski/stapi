package com.cezarykluczynski.stapi.etl.food.creation.processor;

import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FoodWriter implements ItemWriter<Food> {

	private final FoodRepository foodRepository;

	public FoodWriter(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	@Override
	public void write(Chunk<? extends Food> items) throws Exception {
		foodRepository.saveAll(items.getItems());
	}

}
