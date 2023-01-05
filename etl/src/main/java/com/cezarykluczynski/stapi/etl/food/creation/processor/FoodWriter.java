package com.cezarykluczynski.stapi.etl.food.creation.processor;

import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FoodWriter implements ItemWriter<Food> {

	private final FoodRepository foodRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public FoodWriter(FoodRepository foodRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.foodRepository = foodRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(Chunk<? extends Food> items) throws Exception {
		foodRepository.saveAll(process(items));
	}

	private List<Food> process(Chunk<? extends Food> foodList) {
		List<Food> foodListWithoutExtends = fromExtendsListToFoodList(foodList);
		return filterDuplicates(foodListWithoutExtends);
	}

	private List<Food> fromExtendsListToFoodList(Chunk<? extends Food> foodList) {
		return foodList
				.getItems()
				.stream()
				.map(pageAware -> (Food) pageAware)
				.collect(Collectors.toList());
	}

	private List<Food> filterDuplicates(List<Food> foodList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(foodList.stream()
				.map(food -> (PageAware) food)
				.collect(Collectors.toList()), Food.class).stream()
				.map(pageAware -> (Food) pageAware)
				.collect(Collectors.toList());
	}

}
