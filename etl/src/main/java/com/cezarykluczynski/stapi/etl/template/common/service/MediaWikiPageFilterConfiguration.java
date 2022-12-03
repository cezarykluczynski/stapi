package com.cezarykluczynski.stapi.etl.template.common.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Getter
@Builder
public class MediaWikiPageFilterConfiguration {

	private boolean skipRedirects;
	private Collection<String> invalidTitles;
	private Collection<String> invalidPrefixes;
	private Collection<String> invalidSuffixes;
	private Collection<String> invalidCategories;
	private boolean skipPagesSortedOnTopOfAnyCategory;
	private Collection<String> invalidCategoriesToBeSortedOnTopOf;

}
