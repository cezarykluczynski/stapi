package com.cezarykluczynski.stapi.etl.character.common.service;

import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class CharactersPageHeadersProvider {

	private static final List<Pattern> EXCLUDES = Lists.newArrayList(
			Pattern.compile("(Memory_Alpha_images).*"),
			Pattern.compile("(Memory_Alpha_non-canon_redirects).*"));

	private final CategoryApi categoryApi;

	private List<PageHeader> characters;

	public CharactersPageHeadersProvider(CategoryApi categoryApi) {
		this.categoryApi = categoryApi;
	}

	public synchronized List<PageHeader> provide() {
		if (characters == null) {
			characters = Lists.newArrayList();
			characters.addAll(categoryApi.getPagesIncludingSubcategoriesExcluding(CategoryTitle.INDIVIDUALS, EXCLUDES,
					MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.MILITARY_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.Q_CONTINUUM, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.EDUCATORS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.ENTERTAINERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.GOVERNMENT_OFFICIALS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.SCIENTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.HOLOGRAMS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.HOLOGRAPHIC_DUPLICATES, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.FICTIONAL_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.THE_DIXON_HILL_SERIES_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.SHAKESPEARE_CHARACTERS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.ATHLETES, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.DABO_GIRLS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.MUSICIANS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPages(CategoryTitle.INDIVIDUAL_ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters = Lists.newArrayList(Sets.newHashSet(characters));
		}

		return Lists.newArrayList(characters);
	}

}
