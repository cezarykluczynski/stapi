package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.google.common.collect.Lists;
import jakarta.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
class WikitextToEntitiesGenericProcessor {

	private final WikitextApi wikitextApi;

	private final PageApi pageApi;

	private final RepositoryProvider repositoryProvider;

	WikitextToEntitiesGenericProcessor(WikitextApi wikitextApi, PageApi pageApi, RepositoryProvider repositoryProvider) {
		this.wikitextApi = wikitextApi;
		this.pageApi = pageApi;
		this.repositoryProvider = repositoryProvider;
	}

	public <T extends PageAware> List<T> process(String item, Class<T> clazz) {
		Map<Class, CrudRepository> classCrudRepositoryMap = repositoryProvider.provide();
		CrudRepository<T, Long> crudRepository = classCrudRepositoryMap.get(clazz);
		PageAwareRepository<T> pageAwareRepository;
		if (crudRepository instanceof PageAwareRepository) {
			pageAwareRepository = (PageAwareRepository<T>) crudRepository;
		} else {
			log.warn("Could not get PageAwareRepository for class {}", clazz);
			return Lists.newArrayList();
		}

		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> tryFindEntity(pageLinkTitle, pageAwareRepository))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	private <T> Optional<T> tryFindEntity(String pageLinkTitle, PageAwareRepository<T> pageAwareRepository) {
		Optional<T> entityOptional;
		try {
			entityOptional = pageAwareRepository.findByPageTitleWithPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN);
		} catch (NonUniqueResultException e) {
			entityOptional = Optional.empty();
		} catch (Exception e) {
			log.error("Exception thrown when trying to look for entity {} in repository {}: {}.",
					pageLinkTitle, pageAwareRepository.getClass().getSimpleName(), e.getMessage(), e);
			entityOptional = Optional.empty();
		}

		if (!entityOptional.isPresent()) {
			Page page = pageApi.getPage(pageLinkTitle, com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN);
			if (page != null && !page.getRedirectPath().isEmpty()) {
				try {
					entityOptional = pageAwareRepository.findByPageTitleWithPageMediaWikiSource(page.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
				} catch (NonUniqueResultException e) {
					log.error("When searching using page title {} and MediaWiki source {}, non unique result was found.", page.getTitle(),
							MediaWikiSource.MEMORY_ALPHA_EN);
					entityOptional = Optional.empty();
				} catch (Exception e) {
					log.error("Exception thrown when trying to look for entity {} in repository {}: {} (after page was retrieved from source).",
							pageLinkTitle, pageAwareRepository.getClass().getSimpleName(), e.getMessage(), e);
					entityOptional = Optional.empty();
				}
			}
		}

		return entityOptional;
	}


}
