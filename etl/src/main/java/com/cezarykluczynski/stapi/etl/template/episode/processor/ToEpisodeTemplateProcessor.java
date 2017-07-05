package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ToEpisodeTemplateProcessor implements ItemProcessor<Page, EpisodeTemplate> {

	private final EpisodeTemplateProcessor episodeTemplateProcessor;

	private final EpisodeLinkingWorkerComposite episodeLinkingWorkerComposite;

	private final PageBindingService pageBindingService;

	private final SeriesToEpisodeBindingService seriesToEpisodeBindingService;

	private final EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorComposite;

	private final TemplateFinder templateFinder;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Inject
	public ToEpisodeTemplateProcessor(EpisodeTemplateProcessor episodeTemplateProcessor, EpisodeLinkingWorkerComposite episodeLinkingWorkerComposite,
			PageBindingService pageBindingService, SeriesToEpisodeBindingService seriesToEpisodeBindingService,
			EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorComposite, TemplateFinder templateFinder,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.episodeTemplateProcessor = episodeTemplateProcessor;
		this.episodeLinkingWorkerComposite = episodeLinkingWorkerComposite;
		this.pageBindingService = pageBindingService;
		this.seriesToEpisodeBindingService = seriesToEpisodeBindingService;
		this.episodeTemplateEnrichingProcessorComposite = episodeTemplateEnrichingProcessorComposite;
		this.templateFinder = templateFinder;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public EpisodeTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_EPISODE);

		if (!isEpisodePage(item) || !templateOptional.isPresent()) {
			return null;
		}

		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(templateOptional.get());

		if (episodeTemplate == null) {
			return null;
		}

		Episode episodeStub = episodeTemplate.getEpisodeStub();

		setTemplateValuesFromPage(episodeTemplate, item);
		linkPerformancesAndStaff(episodeStub, item);
		enrichTemplateWithPage(episodeTemplate, item);

		return episodeTemplate;
	}

	private void setTemplateValuesFromPage(EpisodeTemplate episodeTemplate, Page item) {
		episodeTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		episodeTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		episodeTemplate.setSeries(seriesToEpisodeBindingService.mapCategoriesToSeries(item.getCategories()));
	}

	private void linkPerformancesAndStaff(Episode episodeStub, Page item) {
		episodeLinkingWorkerComposite.link(item, episodeStub);
	}

	private void enrichTemplateWithPage(EpisodeTemplate episodeTemplate, Page item) throws Exception {
		episodeTemplateEnrichingProcessorComposite.enrich(EnrichablePair.of(item, episodeTemplate));
	}

	private boolean isEpisodePage(Page source) {
		List<String> categoryHeaderList = categoryTitlesExtractingProcessor.process(source.getCategories());

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
