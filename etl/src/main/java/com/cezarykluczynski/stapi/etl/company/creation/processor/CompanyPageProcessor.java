package com.cezarykluczynski.stapi.etl.company.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.company.creation.provider.CompanyNameFixedValueProvider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageName;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CompanyPageProcessor implements ItemProcessor<Page, Company> {

	private PageBindingService pageBindingService;

	private GuidGenerator guidGenerator;

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private CompanyNameFixedValueProvider companyNameFixedValueProvider;

	@Inject
	public CompanyPageProcessor(PageBindingService pageBindingService, GuidGenerator guidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, CompanyNameFixedValueProvider companyNameFixedValueProvider) {
		this.pageBindingService = pageBindingService;
		this.guidGenerator = guidGenerator;
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
		company.setGuid(guidGenerator.generateFromPage(company.getPage(), Company.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		company.setBroadcaster(categoryTitleList.contains(CategoryName.BROADCASTERS));
		company.setCollectibleCompany(categoryTitleList.contains(CategoryName.COLLECTIBLE_COMPANIES));
		company.setConglomerate(categoryTitleList.contains(CategoryName.CONGLOMERATES));
		company.setDigitalVisualEffectsCompany(categoryTitleList.contains(CategoryName.DIGITAL_VISUAL_EFFECTS_COMPANIES));
		company.setDistributor(categoryTitleList.contains(CategoryName.DISTRIBUTORS));
		company.setGameCompany(categoryTitleList.contains(CategoryName.GAME_COMPANIES));
		company.setFilmEquipmentCompany(categoryTitleList.contains(CategoryName.FILM_EQUIPMENT_COMPANIES));
		company.setMakeUpEffectsStudio(categoryTitleList.contains(CategoryName.MAKE_UP_EFFECTS_STUDIOS));
		company.setMattePaintingCompany(categoryTitleList.contains(CategoryName.MATTE_PAINTING_COMPANIES));
		company.setModelAndMiniatureEffectsCompany(categoryTitleList.contains(CategoryName.MODEL_AND_MINIATURE_EFFECTS_COMPANIES));
		company.setPostProductionCompany(categoryTitleList.contains(CategoryName.POST_PRODUCTION_COMPANIES));
		company.setProductionCompany(categoryTitleList.contains(CategoryName.PRODUCTION_COMPANIES));
		company.setPropCompany(categoryTitleList.contains(CategoryName.PROP_COMPANIES));
		company.setRecordLabel(categoryTitleList.contains(CategoryName.RECORD_LABELS));
		company.setSpecialEffectsCompany(categoryTitleList.contains(CategoryName.SPECIAL_EFFECTS_COMPANIES));
		company.setTvAndFilmProductionCompany(categoryTitleList.contains(CategoryName.TV_AND_FILM_PRODUCTION_COMPANIES));
		company.setVideoGameCompany(categoryTitleList.contains(CategoryName.VIDEO_GAME_COMPANIES));

		return company;
	}

	private boolean shouldBeFilteredOut(Page item) {
		return PageName.STAR_TREK_STARSHIP_MINIATURES.equals(item.getTitle());
	}

}
