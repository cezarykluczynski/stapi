package com.cezarykluczynski.stapi.model.page.service

import com.cezarykluczynski.stapi.model.common.repository.InPageAwareRepositoryPageFinder
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class PageBoundToEntityFilteringFinderTest extends Specification {

	private static final Long PAGE_ID_1 = 1L
	private static final Long PAGE_ID_2 = 2L
	private static final Long PAGE_ID_3 = 3L
	private static final Long PAGE_ID_4 = 4L
	private static final Long PAGE_ID_5 = 5L

	private PageRepository pageRepositoryMock

	private InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinderMock

	private PageBoundToEntityFilteringFinder pageBoundToEntityFilteringFinder

	void setup() {
		pageRepositoryMock = Mock()
		inPageAwareRepositoryPageFinderMock = Mock()
		pageBoundToEntityFilteringFinder = new PageBoundToEntityFilteringFinder(pageRepositoryMock, inPageAwareRepositoryPageFinderMock)
	}

	void "returns only pages found by PageRepository that are not found by InPageAwareRepositoryPageFinder"() {
		given:
		List<Page> inPageAwareRepositoryPageFinderPageList = Lists.newArrayList(
				createPage(PAGE_ID_1),
				createPage(PAGE_ID_3),
				createPage(PAGE_ID_5)
		)
		List<Page> pageRepositoryPageList = Lists.newArrayList(
				createPage(PAGE_ID_1),
				createPage(PAGE_ID_2),
				createPage(PAGE_ID_3),
				createPage(PAGE_ID_4)
		)
		Set<Long> allIds = Sets.newHashSet(PAGE_ID_1, PAGE_ID_2, PAGE_ID_3, PAGE_ID_4, PAGE_ID_5)

		when:
		List<Page> pageList = pageBoundToEntityFilteringFinder.find(allIds, Performer)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(allIds, Performer) >> inPageAwareRepositoryPageFinderPageList
		1 * pageRepositoryMock.findByPageIdIn(allIds) >> pageRepositoryPageList
		pageList.size() == 2
		pageList[0].id == PAGE_ID_2
		pageList[1].id == PAGE_ID_4
	}

	private Page createPage(Long pageId) {
		Page page = Mock()
		page.id >> pageId
		page
	}

}
