package com.cezarykluczynski.stapi.etl.video_game.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoGamePageFilterTest extends Specification {

	private VideoGamePageFilter videoGameFilter

	void setup() {
		videoGameFilter = new VideoGamePageFilter()
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeaderRedirect = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeaderRedirect))

		when:
		boolean shouldBeFilteredOut = videoGameFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page title is on list of title to filter out"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(VideoGamePageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = videoGameFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

}
