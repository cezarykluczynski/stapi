package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService;
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodePerformancesLinkingWorker;
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodeStaffLinkingWorker;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryNames;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ToEpisodeTemplateProcessor implements ItemProcessor<Page, EpisodeTemplate> {

	private EpisodeTemplateProcessor episodeTemplateProcessor;

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingWorker;

	private EpisodeStaffLinkingWorker episodeStaffLinkingWorker;

	private PageBindingService pageBindingService;

	private SeriesToEpisodeBindingService seriesToEpisodeBindingService;

	private EpisodeTemplateDatesEnrichingProcessor episodeTemplateDatesEnrichingProcessor;

	private TemplateFinder templateFinder;

	private EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessor;

	@Inject
	public ToEpisodeTemplateProcessor(EpisodeTemplateProcessor episodeTemplateProcessor,
			EpisodePerformancesLinkingWorker episodePerformancesLinkingWorker,
			EpisodeStaffLinkingWorker episodeStaffLinkingWorker, PageBindingService pageBindingService,
			SeriesToEpisodeBindingService seriesToEpisodeBindingService,
			EpisodeTemplateDatesEnrichingProcessor episodeTemplateDatesEnrichingProcessor,
			TemplateFinder templateFinder,
			EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessor) {
		this.episodeTemplateProcessor = episodeTemplateProcessor;
		this.episodePerformancesLinkingWorker = episodePerformancesLinkingWorker;
		this.episodeStaffLinkingWorker = episodeStaffLinkingWorker;
		this.pageBindingService = pageBindingService;
		this.seriesToEpisodeBindingService = seriesToEpisodeBindingService;
		this.episodeTemplateDatesEnrichingProcessor = episodeTemplateDatesEnrichingProcessor;
		this.templateFinder = templateFinder;
		this.episodeTemplateTitleLanguagesEnrichingProcessor = episodeTemplateTitleLanguagesEnrichingProcessor;
	}

	@Override
	public EpisodeTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateName.SIDEBAR_EPISODE);

		if (!isEpisodePage(item) || !templateOptional.isPresent()) {
			return null;
		}

		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(templateOptional.get());

		if (episodeTemplate == null) {
			return null;
		}

		Episode episodeStub = episodeTemplate.getEpisodeStub();
		episodePerformancesLinkingWorker.link(item, episodeStub);
		episodeStaffLinkingWorker.link(item, episodeStub);
		episodeTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(item, episodeTemplate));
		episodeTemplateDatesEnrichingProcessor.enrich(EnrichablePair.of(item, episodeTemplate));

		episodeTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		episodeTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		episodeTemplate.setSeries(seriesToEpisodeBindingService.mapCategoriesToSeries(item.getCategories()));


		return episodeTemplate;
	}

	private boolean isEpisodePage(Page source) {
		List<CategoryHeader> categoryHeaderList = source.getCategories();
		boolean hasEpisodeCategory = categoryHeaderList
				.stream()
				.map(CategoryHeader::getTitle)
				.anyMatch(this::isEpisodeCategory);

		boolean hasProductionListCategory = categoryHeaderList
				.stream()
				.map(CategoryHeader::getTitle)
				.anyMatch(this::isProductionList);

		return hasEpisodeCategory && !hasProductionListCategory;
	}

	private boolean isProductionList(String categoryName) {
		return CategoryName.PRODUCTION_LISTS.equals(categoryName);
	}

	private boolean isEpisodeCategory(String categoryName) {
		return CategoryNames.EPISODES.contains(categoryName);
	}

}
