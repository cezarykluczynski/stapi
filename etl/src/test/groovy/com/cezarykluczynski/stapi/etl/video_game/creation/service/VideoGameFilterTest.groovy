package com.cezarykluczynski.stapi.etl.video_game.creation.service

import com.cezarykluczynski.stapi.etl.template.video_game.service.VideoGameFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoGameFilterTest extends Specification {

	private VideoGameFilter videoGameFilter

	void setup() {
		videoGameFilter = new VideoGameFilter()
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
		Page page = new Page(title: RandomUtil.randomItem(VideoGameFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = videoGameFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

}
