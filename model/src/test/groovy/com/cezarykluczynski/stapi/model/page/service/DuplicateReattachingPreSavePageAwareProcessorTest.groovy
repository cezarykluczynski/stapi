package com.cezarykluczynski.stapi.model.page.service

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.google.common.collect.Lists
import spock.lang.Specification


class DuplicateReattachingPreSavePageAwareProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L

	private PageRepository pageRepositoryMock

	private DuplicateReattachingPreSavePageAwareProcessor tolerantPreSavePageAwareProcessor

	def setup() {
		pageRepositoryMock = Mock(PageRepository)
		tolerantPreSavePageAwareProcessor = new DuplicateReattachingPreSavePageAwareProcessor(pageRepositoryMock)
	}

	def "attaches database entities when there are duplicates"() {
		given:
		Page nonPersistedPage = new Page(pageId: PAGE_ID)
		Page persistedPage = new Page(pageId: PAGE_ID)

		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> nonPersistedPage
				}
		)

		when:
		List<PageAware> pageAwareListOutput = tolerantPreSavePageAwareProcessor.process(pageAwareList)

		then:
		1 * pageRepositoryMock.findByPageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			return Lists.newArrayList(persistedPage)
		}
		pageAwareListOutput[0].page == persistedPage
	}

	def "returns original collection when there is no duplicates"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID)
				}
		)

		when:
		List<PageAware> pageAwareListOutput = tolerantPreSavePageAwareProcessor.process(pageAwareList)

		then:
		1 * pageRepositoryMock.findByPageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			return Lists.newArrayList()
		}
		pageAwareListOutput == pageAwareList
	}

}
