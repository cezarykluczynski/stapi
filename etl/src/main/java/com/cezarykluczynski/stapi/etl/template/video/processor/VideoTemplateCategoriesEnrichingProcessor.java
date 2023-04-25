package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategoryFinder;
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoTemplateCategoriesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private static final String SPACE_AND_BRACKET = " (";

	private final CategoryFinder categoryFinder;

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private final VideoReleaseRepository videoReleaseRepository;

	private final PageApi pageApi;

	private final Map<String, Page> pageCache = Maps.newLinkedHashMap();

	@Override
	public void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		final Page page = enrichablePair.getInput();
		final VideoTemplate videoTemplate = enrichablePair.getOutput();
		videoTemplate.setDocumentary(categoryFinder.hasAnyCategory(page, List.of(CategoryTitle.DOCUMENTARIES)));
		videoTemplate.setSpecialFeatures(categoryFinder.hasAnyCategory(page, List.of(CategoryTitle.SPECIAL_FEATURES)));

		final String pageTitle = page.getTitle();
		final MediaWikiSource mediaWikiSource = page.getMediaWikiSource();
		if (Boolean.FALSE.equals(videoTemplate.getDocumentary()) && Boolean.FALSE.equals(videoTemplate.getSpecialFeatures())
				&& pageTitle.contains(SPACE_AND_BRACKET)) {
			String baseTitleCandidate = StringUtil.substringBeforeAll(pageTitle, List.of(SPACE_AND_BRACKET));
			final Optional<VideoRelease> baseVideoReleaseOptional = videoReleaseRepository.findByPageTitleAndPageMediaWikiSource(baseTitleCandidate,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
			if (baseVideoReleaseOptional.isPresent()) {
				VideoRelease baseVideoRelease = baseVideoReleaseOptional.get();
				if (Boolean.TRUE.equals(baseVideoRelease.getDocumentary())) {
					videoTemplate.setDocumentary(true);
				}
				if (Boolean.TRUE.equals(baseVideoRelease.getSpecialFeatures())) {
					videoTemplate.setSpecialFeatures(true);
				}
			} else {
				Page basePage;
				if (pageCache.containsKey(baseTitleCandidate)) {
					basePage = pageCache.get(baseTitleCandidate);
				} else {
					basePage = pageApi.getPage(baseTitleCandidate, mediaWikiSource);
					pageCache.put(baseTitleCandidate, basePage);
				}
				if (basePage != null) {
					if (Boolean.FALSE.equals(videoTemplate.getDocumentary())) {
						videoTemplate.setDocumentary(categoryFinder.hasAnyCategory(basePage, List.of(CategoryTitle.DOCUMENTARIES)));
					}
					if (Boolean.FALSE.equals(videoTemplate.getSpecialFeatures())) {
						videoTemplate.setSpecialFeatures(categoryFinder.hasAnyCategory(basePage, List.of(CategoryTitle.SPECIAL_FEATURES)));
					}
				}
			}
		}
	}

}
