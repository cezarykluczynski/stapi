package com.cezarykluczynski.stapi.etl.food.creation.processor;

import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
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
	public void write(List<? extends Food> items) throws Exception {
		foodRepository.save(process(items));
	}

	private List<Food> process(List<? extends Food> foodList) {
		List<Food> foodListWithoutExtends = fromExtendsListToFoodList(foodList);
		return filterDuplicates(foodListWithoutExtends);
	}

	private List<Food> fromExtendsListToFoodList(List<? extends Food> foodList) {
		return foodList
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
