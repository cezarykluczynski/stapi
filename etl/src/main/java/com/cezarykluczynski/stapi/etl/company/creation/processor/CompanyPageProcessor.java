package com.cezarykluczynski.stapi.etl.company.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.company.creation.provider.CompanyNameFixedValueProvider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyPageProcessor implements ItemProcessor<Page, Company> {

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final CompanyNameFixedValueProvider companyNameFixedValueProvider;

	public CompanyPageProcessor(PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, CompanyNameFixedValueProvider companyNameFixedValueProvider) {
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.companyNameFixedValueProvider = companyNameFixedValueProvider;
	}

	@Override
	public Company process(Page item) throws Exception {
		if (!item.getRedirectPath().isEmpty() || shouldBeFilteredOut(item)) {
			return null;
		}

		Company company = new Company();
		FixedValueHolder<String> titleFixedValueHolder = companyNameFixedValueProvider.getSearchedValue(item.getTitle());
		company.setName(titleFixedValueHolder.isFound() ? titleFixedValueHolder.getValue() : item.getTitle());

		company.setPage(pageBindingService.fromPageToPageEntity(item));
		company.setUid(uidGenerator.generateFromPage(company.getPage(), Company.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		company.setBroadcaster(categoryTitleList.contains(CategoryTitle.BROADCASTERS));
		company.setCollectibleCompany(categoryTitleList.contains(CategoryTitle.COLLECTIBLE_COMPANIES));
		company.setConglomerate(categoryTitleList.contains(CategoryTitle.CONGLOMERATES));
		company.setDigitalVisualEffectsCompany(categoryTitleList.contains(CategoryTitle.DIGITAL_VISUAL_EFFECTS_COMPANIES));
		company.setDistributor(categoryTitleList.contains(CategoryTitle.DISTRIBUTORS));
		company.setGameCompany(categoryTitleList.contains(CategoryTitle.GAME_COMPANIES));
		company.setFilmEquipmentCompany(categoryTitleList.contains(CategoryTitle.FILM_EQUIPMENT_COMPANIES));
		company.setMakeUpEffectsStudio(categoryTitleList.contains(CategoryTitle.MAKE_UP_EFFECTS_STUDIOS));
		company.setMattePaintingCompany(categoryTitleList.contains(CategoryTitle.MATTE_PAINTING_COMPANIES));
		company.setModelAndMiniatureEffectsCompany(categoryTitleList.contains(CategoryTitle.MODEL_AND_MINIATURE_EFFECTS_COMPANIES));
		company.setPostProductionCompany(categoryTitleList.contains(CategoryTitle.POST_PRODUCTION_COMPANIES));
		company.setProductionCompany(categoryTitleList.contains(CategoryTitle.PRODUCTION_COMPANIES));
		company.setPropCompany(categoryTitleList.contains(CategoryTitle.PROP_COMPANIES));
		company.setRecordLabel(categoryTitleList.contains(CategoryTitle.RECORD_LABELS));
		company.setSpecialEffectsCompany(categoryTitleList.contains(CategoryTitle.SPECIAL_EFFECTS_COMPANIES));
		company.setTvAndFilmProductionCompany(categoryTitleList.contains(CategoryTitle.TV_AND_FILM_PRODUCTION_COMPANIES));
		company.setVideoGameCompany(categoryTitleList.contains(CategoryTitle.VIDEO_GAME_COMPANIES));

		return company;
	}

	private boolean shouldBeFilteredOut(Page item) {
		return PageTitle.STAR_TREK_STARSHIP_MINIATURES.equals(item.getTitle());
	}

}
