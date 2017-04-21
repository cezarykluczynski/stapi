package com.cezarykluczynski.stapi.model.page.service

import com.cezarykluczynski.stapi.model.common.repository.InPageAwareRepositoryPageFinder
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.google.common.collect.Lists
import spock.lang.Specification

class DuplicateFilteringPreSavePageAwareFilterTest extends Specification {

	private static final Long PAGE_ID_1 = 1
	private static final Long PAGE_ID_2 = 2

	private InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinderMock

	private DuplicateFilteringPreSavePageAwareFilter filteringPreSavePageAwareProcessor

	void setup() {
		inPageAwareRepositoryPageFinderMock = Mock()
		filteringPreSavePageAwareProcessor = new DuplicateFilteringPreSavePageAwareFilter(inPageAwareRepositoryPageFinderMock)
	}

	void "filters duplicated entities from chunk"() {
		given:

		Page originalPage = new Page(pageId: PAGE_ID_1)
		Page duplicatePage = new Page(pageId: PAGE_ID_1)
		PageAware originalPageAware = Mock()
		originalPageAware.page >> originalPage
		PageAware duplicatePageAware = Mock()
		duplicatePageAware.page >> duplicatePage
		List<PageAware> pageAwareList = Lists.newArrayList(originalPageAware, duplicatePageAware)

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID_1)
			Lists.newArrayList()
		}
		pageAwareListOutput.size() == 1
		pageAwareListOutput[0] == originalPageAware
	}

	void "filters duplicated entities when they are already persisted"() {
		given:
		PageAware pageAware1 = Mock()
		pageAware1.page >> new Page(pageId: PAGE_ID_1)
		PageAware pageAware2 = Mock()
		pageAware2.page >> new Page(pageId: PAGE_ID_2)
		PageAware pageAware3 = Mock()
		pageAware3.page >> new Page(pageId: PAGE_ID_1)
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware1, pageAware2, pageAware3)

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID_1)
			Lists.newArrayList(new Page(pageId: PAGE_ID_1))
		}
		pageAwareListOutput.size() == 1
		pageAwareListOutput[0].page.pageId == PAGE_ID_2
	}

	void "returns original collection when there is no duplicates"() {
		given:
		PageAware pageAware = Mock()
		pageAware.page >> new Page(pageId: PAGE_ID_1)
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware)

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID_1)
			Lists.newArrayList()
		}
		pageAwareListOutput == pageAwareList
	}

	void "does no call InPageAwareRepositoryPageFinder when page list is empty"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList()

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		0 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_)
		pageAwareListOutput == pageAwareList
	}

}
