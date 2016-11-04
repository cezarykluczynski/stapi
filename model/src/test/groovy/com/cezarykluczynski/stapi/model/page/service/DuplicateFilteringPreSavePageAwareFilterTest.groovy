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

	def setup() {
		inPageAwareRepositoryPageFinderMock = Mock(InPageAwareRepositoryPageFinder)
		filteringPreSavePageAwareProcessor = new DuplicateFilteringPreSavePageAwareFilter(inPageAwareRepositoryPageFinderMock)
	}

	def "filters duplicated entities from chunk"() {
		given:

		Page originalPage = new Page(pageId: PAGE_ID_1)
		Page duplicatePage = new Page(pageId: PAGE_ID_1)
		PageAware originalPageAware = Mock(PageAware) {
			getPage() >> originalPage
		}
		PageAware duplicatePageAware = Mock(PageAware) {
			getPage() >> duplicatePage
		}
		List<PageAware> pageAwareList = Lists.newArrayList(originalPageAware, duplicatePageAware)

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID_1)
			return Lists.newArrayList()
		}
		pageAwareListOutput.size() == 1
		pageAwareListOutput[0] == originalPageAware
	}

	def "filters duplicated entities when they are already persisted"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID_1)
				},
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID_2)
				},
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID_1)
				}
		)

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID_1)
			return Lists.newArrayList(new Page(pageId: PAGE_ID_1))
		}
		pageAwareListOutput.size() == 1
		pageAwareListOutput[0].page.pageId == PAGE_ID_2
	}

	def "returns original collection when there is no duplicates"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID_1)
				}
		)

		when:
		List<PageAware> pageAwareListOutput = filteringPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * inPageAwareRepositoryPageFinderMock.findByPagePageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID_1)
			return Lists.newArrayList()
		}
		pageAwareListOutput == pageAwareList
	}

}
