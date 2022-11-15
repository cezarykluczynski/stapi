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
import org.assertj.core.util.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPageProcessor implements ItemProcessor<Page, Food> {

	private static final List<String> VALID_BRACKET_SUFFIXES = Lists.newArrayList(" (Vulcan)");

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
		food.setName(getName(item));

		food.setPage(pageBindingService.fromPageToPageEntity(item));
		food.setUid(uidGenerator.generateFromPage(food.getPage(), Food.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		food.setEarthlyOrigin(StringUtil.anyStartsWithIgnoreCase(categoryTitleList, PageTitle.EARTH));
		food.setDessert(categoryTitleList.contains(CategoryTitle.DESSERTS));
		food.setFruit(categoryTitleList.contains(CategoryTitle.FRUITS));
		food.setHerbOrSpice(categoryTitleList.contains(CategoryTitle.HERBS_AND_SPICES)
				|| categoryTitleList.contains(CategoryTitle.EARTH_HERBS_AND_SPICES));
		food.setSauce(categoryTitleList.contains(CategoryTitle.SAUCES));
		food.setSoup(categoryTitleList.contains(CategoryTitle.SOUPS));
		food.setAlcoholicBeverage(categoryTitleList.contains(CategoryTitle.ALCOHOLIC_BEVERAGES));
		food.setJuice(categoryTitleList.contains(CategoryTitle.JUICES));
		food.setTea(categoryTitleList.contains(CategoryTitle.TEA));
		food.setBeverage(categoryTitleList.contains(CategoryTitle.BEVERAGES) || categoryTitleList.contains(CategoryTitle.EARTH_BEVERAGES)
				|| food.getAlcoholicBeverage() || food.getJuice() || food.getTea());

		return food;
	}

	private String getName(Page item) {
		String pageTitle = item.getTitle();
		if (pageTitle != null && StringUtil.endsWithAny(pageTitle, VALID_BRACKET_SUFFIXES)) {
			return pageTitle;
		}
		return TitleUtil.getNameFromTitle(pageTitle);
	}

}
