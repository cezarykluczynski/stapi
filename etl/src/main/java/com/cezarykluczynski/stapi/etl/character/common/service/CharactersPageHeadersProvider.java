package com.cezarykluczynski.stapi.etl.character.common.service;

import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CharactersPageHeadersProvider {

	private static final List<Pattern> EXCLUDES = Lists.newArrayList(
			Pattern.compile("(Memory_Alpha_images).*"),
			Pattern.compile("(Memory_Alpha_non-canon_redirects).*")
	);

	private final CategoryApi categoryApi;

	private List<PageHeader> characters;

	public CharactersPageHeadersProvider(CategoryApi categoryApi) {
		this.categoryApi = categoryApi;
	}

	@SuppressWarnings("VariableDeclarationUsageDistance")
	public synchronized List<PageHeader> provide() {
		if (characters == null) {
			Stopwatch stopwatch = Stopwatch.createStarted();
			characters = Lists.newArrayList();
			characters.addAll(categoryApi.getPagesIncludingSubcategoriesExcluding(CategoryTitle.INDIVIDUALS, EXCLUDES, 3,
					MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.GOVERNMENT_OFFICIALS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.SCIENTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.MONARCHS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters = SortingUtil.sortedUnique(characters);
			log.info("Grabbing characters took {} minutes.", stopwatch.elapsed(TimeUnit.MINUTES));
		}

		return ImmutableList.copyOf(characters);
	}

}
