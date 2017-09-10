package com.cezarykluczynski.stapi.etl.character.common.service;

import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CharactersPageHeadersProvider {

	private final CategoryApi categoryApi;

	private List<PageHeader> characters;

	@Inject
	public CharactersPageHeadersProvider(CategoryApi categoryApi) {
		this.categoryApi = categoryApi;
	}

	public synchronized List<PageHeader> provide() {
		if (characters == null) {
			characters = Lists.newArrayList();
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.INDIVIDUALS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.MILITARY_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.Q_CONTINUUM, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.HOLOGRAMS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.HOLOGRAPHIC_DUPLICATES, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.FICTIONAL_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.THE_DIXON_HILL_SERIES_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.SHAKESPEARE_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters = Lists.newArrayList(Sets.newHashSet(characters));
		}

		return Lists.newArrayList(characters);
	}

}
