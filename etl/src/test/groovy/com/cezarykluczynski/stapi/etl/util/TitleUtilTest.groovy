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

	void "converts title to title that can be put into MediaWiki API query"() {
		expect:
		TitleUtil.toMediaWikiTitle('Title') == 'Title'
		TitleUtil.toMediaWikiTitle('Title title') == 'Title_title'
		TitleUtil.toMediaWikiTitle('Title title#section') == 'Title_title#section'
		TitleUtil.toMediaWikiTitle('Title title#section section') == 'Title_title#section_section'
	}

}
