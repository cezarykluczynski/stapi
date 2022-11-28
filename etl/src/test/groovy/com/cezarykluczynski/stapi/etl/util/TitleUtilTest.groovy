package com.cezarykluczynski.stapi.etl.util

import spock.lang.Specification

class TitleUtilTest extends Specification {

	void "clears title"() {
		expect:
		TitleUtil.getNameFromTitle('Title') == 'Title'
		TitleUtil.getNameFromTitle(' Title') == 'Title'
		TitleUtil.getNameFromTitle('Title ') == 'Title'
		TitleUtil.getNameFromTitle('Title (civilian)') == 'Title'
		TitleUtil.getNameFromTitle(' Title (civilian)') == 'Title'
		TitleUtil.getNameFromTitle(' Title (civilian) ') == 'Title'
		TitleUtil.getNameFromTitle('Title （エピソード）') == 'Title'
		TitleUtil.getNameFromTitle(' Title （エピソード）') == 'Title'
		TitleUtil.getNameFromTitle(' Title （エピソード） ') == 'Title'
	}

	void "clears title only if it doesn't end with right bracket"() {
		expect:
		TitleUtil.getNameFromTitleIfBracketsEndsString('Title') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString('Title ') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString('Title (civilian)') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title (civilian)') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title (civilian) ') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString('Title （エピソード）') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title （エピソード）') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title （エピソード） ') == 'Title'
		TitleUtil.getNameFromTitleIfBracketsEndsString('Title (civilian) starship') == 'Title (civilian) starship'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title (civilian) starship ') == 'Title (civilian) starship'
		TitleUtil.getNameFromTitleIfBracketsEndsString('Title （エピソード） starship') == 'Title （エピソード） starship'
		TitleUtil.getNameFromTitleIfBracketsEndsString(' Title （エピソード） starship ') == 'Title （エピソード） starship'
	}

	void "converts title to title that can be put into MediaWiki API query"() {
		expect:
		TitleUtil.toMediaWikiTitle('Title') == 'Title'
		TitleUtil.toMediaWikiTitle('Title title') == 'Title_title'
		TitleUtil.toMediaWikiTitle('Title title#section') == 'Title_title#section'
		TitleUtil.toMediaWikiTitle('Title title#section section') == 'Title_title#section_section'
	}

}
