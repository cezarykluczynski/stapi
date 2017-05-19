package com.cezarykluczynski.stapi.etl.food.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.food.creation.service.FoodPageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPageProcessor implements ItemProcessor<Page, Food> {

	private final FoodPageFilter foodPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public FoodPageProcessor(FoodPageFilter foodPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.foodPageFilter = foodPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Food process(Page item) throws Exception {
		if (foodPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Food food = new Food();
		food.setName(TitleUtil.getNameFromTitle(item.getTitle()));

		food.setPage(pageBindingService.fromPageToPageEntity(item));
		food.setUid(uidGenerator.generateFromPage(food.getPage(), Food.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		food.setEarthlyOrigin(StringUtil.anyStartsWithIgnoreCase(categoryTitleList, PageTitle.EARTH));
		food.setDessert(categoryTitleList.contains(CategoryTitle.DESSERTS));
		food.setFruit(categoryTitleList.contains(CategoryTitle.FRUITS));
		food.setHerbOrSpice(StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.HERBS_AND_SPICES));
		food.setSauce(categoryTitleList.contains(CategoryTitle.SAUCES));
		food.setSoup(categoryTitleList.contains(CategoryTitle.SOUPS));
		food.setAlcoholicBeverage(categoryTitleList.contains(CategoryTitle.ALCOHOLIC_BEVERAGES));
		food.setJuice(categoryTitleList.contains(CategoryTitle.JUICES));
		food.setTea(categoryTitleList.contains(CategoryTitle.TEA));
		food.setBeverage(StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.BEVERAGES) || food.getAlcoholicBeverage()
				|| food.getJuice() || food.getTea());

		return food;
	}

}
