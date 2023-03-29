package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.EpisodeTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData;
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider;
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodeLinkingWorkerComposite;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("ParameterNumber")
public class ToEpisodeTemplateProcessor implements ItemProcessor<Page, EpisodeTemplate> {

	private final EpisodeLinkingWorkerComposite episodeLinkingWorkerComposite;

	private final PageBindingService pageBindingService;

	private final EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorComposite;

	private final TemplateFinder templateFinder;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final ModuleEpisodeDataEnrichingProcessor moduleEpisodeDataEnrichingProcessor;

	private final EpisodeTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessor;

	private final ModuleEpisodeDataProvider moduleEpisodeDataProvider;

	@Override
	public EpisodeTemplate process(Page item) throws Exception {
		List<String> categoryHeaderList = categoryTitlesExtractingProcessor.process(item.getCategories());
		boolean isEpisodePage = isEpisodePage(categoryHeaderList);

		if (!isEpisodePage) {
			log.info("Page with title \"{}\" marked as not an episode (excluding category present).", item.getTitle());
			return null;
		}

		EpisodeTemplate episodeTemplate = new EpisodeTemplate();
		episodeTemplate.setEpisodeStub(new Episode());
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_EPISODE);
		if (templateOptional.isPresent()) {
			episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(templateOptional.get(), episodeTemplate));
		}
		setTemplateValuesFromPage(episodeTemplate, item);
		final ModuleEpisodeData moduleEpisodeData = moduleEpisodeDataProvider.provideDataFor(item.getTitle());
		if (moduleEpisodeData == null) {
			log.info("Page with title \"{}\" has no entry in module episode data, skipping.", item.getTitle());
			return null;
		}
		moduleEpisodeDataEnrichingProcessor.enrich(EnrichablePair.of(moduleEpisodeData, episodeTemplate));
		Episode episodeStub = episodeTemplate.getEpisodeStub();
		linkPerformancesAndStaff(episodeStub, item);
		enrichTemplateWithPage(episodeTemplate, item);
		if (!Boolean.TRUE.equals(episodeTemplate.getFeatureLength())) {
			episodeTemplate.setFeatureLength(categoryHeaderList.contains(CategoryTitle.DOUBLE_LENGTH_EPISODES));
		}
		Preconditions.checkNotNull(episodeTemplate.getSeason(), "Season has to be set in episode %s", episodeTemplate.getTitle());
		Preconditions.checkNotNull(episodeTemplate.getSeries(), "Series has to be set in episode %s", episodeTemplate.getTitle());
		return episodeTemplate;
	}

	private void setTemplateValuesFromPage(EpisodeTemplate episodeTemplate, Page item) {
		episodeTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		episodeTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
	}

	private void linkPerformancesAndStaff(Episode episodeStub, Page item) {
		episodeLinkingWorkerComposite.link(item, episodeStub);
	}

	private void enrichTemplateWithPage(EpisodeTemplate episodeTemplate, Page item) throws Exception {
		episodeTemplateEnrichingProcessorComposite.enrich(EnrichablePair.of(item, episodeTemplate));
	}

	private boolean isEpisodePage(List<String> categoryHeaderList) {
		boolean hasEpisodeCategory = categoryHeaderList.stream()
				.anyMatch(this::isEpisodeCategory);

		boolean hasProductionListCategory = categoryHeaderList.stream()
				.anyMatch(this::isProductionList);

		return hasEpisodeCategory && !hasProductionListCategory;
	}

	private boolean isProductionList(String categoryName) {
		return CategoryTitle.PRODUCTION_LISTS.equals(categoryName);
	}

	private boolean isEpisodeCategory(String categoryName) {
		return CategoryTitles.EPISODES.contains(categoryName);
	}

}
