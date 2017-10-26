package com.cezarykluczynski.stapi.etl.animal.creation.processor;

import com.cezarykluczynski.stapi.etl.animal.creation.service.AnimalPageFilter;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalPageProcessor implements ItemProcessor<Page, Animal> {

	private final AnimalPageFilter animalPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public AnimalPageProcessor(AnimalPageFilter animalPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.animalPageFilter = animalPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public Animal process(Page item) throws Exception {
		if (animalPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Animal animal = new Animal();
		animal.setName(TitleUtil.getNameFromTitle(item.getTitle()));

		animal.setPage(pageBindingService.fromPageToPageEntity(item));
		animal.setUid(uidGenerator.generateFromPage(animal.getPage(), Animal.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		animal.setEarthInsect(categoryTitleList.contains(CategoryTitle.EARTH_INSECTS));
		animal.setEarthAnimal(animal.getEarthInsect() || categoryTitleList.contains(CategoryTitle.EARTH_ANIMALS)
				|| categoryTitleList.contains(CategoryTitle.EARTH_ANIMALS_RETCONNED));
		animal.setAvian(categoryTitleList.contains(CategoryTitle.AVIANS));
		animal.setCanine(categoryTitleList.contains(CategoryTitle.CANINES));
		animal.setFeline(categoryTitleList.contains(CategoryTitle.FELINES));

		return animal;
	}

}
