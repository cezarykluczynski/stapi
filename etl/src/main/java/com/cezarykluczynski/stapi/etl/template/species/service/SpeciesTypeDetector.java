package com.cezarykluczynski.stapi.etl.template.species.service;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.template.species.processor.SpeciesTypeFixedValueProvider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpeciesTypeDetector implements InitializingBean {

	private final Map<String, List<String>> speciesTypesToPageTitles = Maps.newHashMap();

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final StepCompletenessDecider stepCompletenessDecider;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final ParagraphExtractor paragraphExtractor;

	private final SpeciesTypeFixedValueProvider speciesTypeFixedValueProvider;

	public SpeciesTypeDetector(PageApi pageApi, WikitextApi wikitextApi, StepCompletenessDecider stepCompletenessDecider,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, ParagraphExtractor paragraphExtractor,
			SpeciesTypeFixedValueProvider speciesTypeFixedValueProvider) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
		this.stepCompletenessDecider = stepCompletenessDecider;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.paragraphExtractor = paragraphExtractor;
		this.speciesTypeFixedValueProvider = speciesTypeFixedValueProvider;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPECIES)) {
			return;
		}

		initializeKey(PageTitle.DELTA_QUADRANT_SPECIES);
		initializeKey(PageTitle.EXTRA_GALACTIC_SPECIES);
		initializeKey(PageTitle.GAMMA_QUADRANT_SPECIES);
		initializeKey(PageTitle.HUMANOID_SPECIES);
		initializeKey(PageTitle.NON_CORPOREAL_SPECIES);
		initializeKey(PageTitle.SHAPESHIFTING_SPECIES);
		initializeKey(PageTitle.SPACEBORNE_SPECIES);
		initializeKey(PageTitle.TELEPATHIC_SPECIES);
		initializeKey(PageTitle.TRANS_DIMENSIONAL_SPECIES);
	}

	public boolean isDeltaQuadrantSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.DELTA_QUADRANT_SPECIES).contains(page.getTitle());
	}

	public boolean isWarpCapableSpecies(Page page) {
		FixedValueHolder<Boolean> speciesFixedValueHolder = speciesTypeFixedValueProvider.getSearchedValue(page.getTitle());
		return speciesFixedValueHolder.isFound() && speciesFixedValueHolder.getValue()
				|| wikitextApi.getPageLinksFromWikitext(getFirstParagraph(page)).stream().anyMatch(this::isWarpCapableLink);
	}

	public boolean isExtraGalacticSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.EXTRA_GALACTIC_SPECIES).contains(page.getTitle());
	}

	public boolean isGammaQuadrantSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.GAMMA_QUADRANT_SPECIES).contains(page.getTitle());
	}

	public boolean isHumanoidSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.HUMANOID_SPECIES).contains(page.getTitle());
	}

	public boolean isReptilianSpecies(Page page) {
		return wikitextApi.getPageLinksFromWikitext(getFirstParagraph(page)).stream()
				.anyMatch(this::isReptileLink);
	}

	public boolean isNonCorporealSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.NON_CORPOREAL_SPECIES).contains(page.getTitle());
	}

	public boolean isShapeshiftingSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.SHAPESHIFTING_SPECIES).contains(page.getTitle());
	}

	public boolean isSpaceborneSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.SPACEBORNE_SPECIES).contains(page.getTitle());
	}

	public boolean isTelepathicSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.TELEPATHIC_SPECIES).contains(page.getTitle());
	}

	public boolean isTransDimensionalSpecies(Page page) {
		return speciesTypesToPageTitles.get(PageTitle.TRANS_DIMENSIONAL_SPECIES).contains(page.getTitle());
	}

	public boolean isUnnamedSpecies(Page page) {
		return categoryTitlesExtractingProcessor.process(page.getCategories()).contains(CategoryTitle.UNNAMED_SPECIES);
	}

	private void initializeKey(String pageTitle) {
		Page page = pageApi.getPage(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN);
		if (page == null) {
			speciesTypesToPageTitles.put(pageTitle, Lists.newArrayList());
			log.info("Could not get page \"{}\"", pageTitle);
			return;
		}

		speciesTypesToPageTitles.put(pageTitle, wikitextApi.getPageTitlesFromWikitext(page.getWikitext()));
	}

	private boolean isWarpCapableLink(PageLink pageLink) {
		String pageTitle = pageLink.getTitle();
		String pageDescription = pageLink.getDescription();

		boolean isWarpCapablePageTitle = StringUtils.equalsIgnoreCase(pageTitle, PageTitle.WARP_CAPABLE);
		boolean isWarpDrivePageTitle = StringUtils.equalsIgnoreCase(pageTitle, PageTitle.WARP_DRIVE);
		boolean isEmptyPageDescription = pageDescription == null;
		boolean isWarpCapablePageDescription = StringUtils.equalsIgnoreCase(pageDescription, PageTitle.WARP_CAPABLE);

		return isWarpCapablePageTitle && (isEmptyPageDescription || isWarpCapablePageDescription)
				|| isWarpDrivePageTitle && isWarpCapablePageDescription;
	}

	private boolean isReptileLink(PageLink pageLink) {
		return StringUtils.equalsIgnoreCase(pageLink.getTitle(), PageTitle.REPTILE);
	}

	private String getFirstParagraph(Page page) {
		List<String> paragraphList = paragraphExtractor.extractParagraphs(wikitextApi.getWikitextWithoutTemplates(page.getWikitext()))
				.stream()
				.filter(StringUtils::isNotBlank)
				.collect(Collectors.toList());

		if (paragraphList.isEmpty()) {
			return "";
		}

		return paragraphList.get(0);
	}

}
