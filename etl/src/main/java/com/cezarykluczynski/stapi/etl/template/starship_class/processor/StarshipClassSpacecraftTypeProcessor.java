package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Sets;
import info.bliki.api.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class StarshipClassSpacecraftTypeProcessor implements ItemProcessor<String, Set<SpacecraftType>> {

	private static final MediaWikiSource MODEL_MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;
	private static final com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource SOURCES_MEDIA_WIKI_SOURCE
			= com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN;

	private final WikitextApi wikitextApi;

	private final StarshipClassTemplateNameCorrectionFixedValueProvider starshipClassTemplateNameCorrectionFixedValueProvider;

	private final PageApi pageApi;

	private final SpacecraftTypeRepository spacecraftTypeRepository;

	private final Set<String> loggedMissingTypes = Sets.newHashSet();

	public StarshipClassSpacecraftTypeProcessor(WikitextApi wikitextApi,
			StarshipClassTemplateNameCorrectionFixedValueProvider starshipClassTemplateNameCorrectionFixedValueProvider, PageApi pageApi,
			SpacecraftTypeRepository spacecraftTypeRepository) {
		this.wikitextApi = wikitextApi;
		this.starshipClassTemplateNameCorrectionFixedValueProvider = starshipClassTemplateNameCorrectionFixedValueProvider;
		this.pageApi = pageApi;
		this.spacecraftTypeRepository = spacecraftTypeRepository;
	}

	@Override
	@NonNull
	public Set<SpacecraftType> process(String item) throws Exception {
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(item);
		Set<SpacecraftType> spacecraftTypeSet = Sets.newHashSet();

		pageTitleList.forEach(pageTitle -> {
			String normalizedPageTitle = normalizePageTitle(pageTitle);

			Optional<SpacecraftType> spacecraftTypeOptional = spacecraftTypeRepository.findByNameIgnoreCase(normalizedPageTitle);
			if (spacecraftTypeOptional.isPresent()) {
				spacecraftTypeSet.add(spacecraftTypeOptional.get());
			} else {
				tryExtractUsingCanonicalPageTitle(normalizedPageTitle, spacecraftTypeSet);
			}
		});

		return spacecraftTypeSet;
	}

	private void tryExtractUsingCanonicalPageTitle(String pageTitle, Set<SpacecraftType> spacecraftTypeSet) {
		final Optional<SpacecraftType> spacecraftTypeOptional;
		spacecraftTypeOptional = spacecraftTypeRepository.findByPageTitleWithPageMediaWikiSource(pageTitle, MODEL_MEDIA_WIKI_SOURCE);

		if (spacecraftTypeOptional.isPresent()) {
			spacecraftTypeSet.add(spacecraftTypeOptional.get());
		} else {
			tryExtractTypeUsingApi(pageTitle, spacecraftTypeSet);
		}
	}

	private void tryExtractTypeUsingApi(String pageTitle, Set<SpacecraftType> spacecraftTypeSet) {
		Page page = pageApi.getPage(pageTitle, SOURCES_MEDIA_WIKI_SOURCE);

		if (page != null) {
			PageInfo pageInfo = pageApi.getPageInfo(page.getTitle(), SOURCES_MEDIA_WIKI_SOURCE);
			if (pageInfo != null) {
				Optional<SpacecraftType> spacecraftTypeOptional = spacecraftTypeRepository
						.findByPagePageIdAndPageMediaWikiSource(Long.valueOf(pageInfo.getPageid()), MODEL_MEDIA_WIKI_SOURCE);

				if (spacecraftTypeOptional.isPresent()) {
					spacecraftTypeSet.add(spacecraftTypeOptional.get());
				} else {
					doLogMissingEntityByTitle(pageTitle);
				}
			} else {
				doLogMissingEntityByTitle(pageTitle);
			}
		} else {
			doLogMissingEntityByTitle(pageTitle);
		}
	}

	private String normalizePageTitle(String pageTitle) {
		String pageTitleUcFirst = StringUtil.upperCaseFirst(pageTitle);
		FixedValueHolder<String> correctedTitleFixedValueHolder = starshipClassTemplateNameCorrectionFixedValueProvider
				.getSearchedValue(pageTitleUcFirst);

		if (correctedTitleFixedValueHolder.isFound()) {
			pageTitleUcFirst = correctedTitleFixedValueHolder.getValue();
		}
		return pageTitleUcFirst;
	}

	private void doLogMissingEntityByTitle(String pageTitle) {
		if (!loggedMissingTypes.contains(pageTitle)) {
			log.info("Could not find spacecraft type \"{}\" in local database.", pageTitle);
			loggedMissingTypes.add(pageTitle);
		}
	}

}
