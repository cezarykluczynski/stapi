package com.cezarykluczynski.stapi.model.page.service

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class DuplicateReattachingPreSavePageAwareFilterTest extends Specification {

	private static final Long PAGE_ID = 1L

	private PageRepository pageRepositoryMock

	private DuplicateReattachingPreSavePageAwareFilter tolerantPreSavePageAwareProcessor

	void setup() {
		pageRepositoryMock = Mock()
		tolerantPreSavePageAwareProcessor = new DuplicateReattachingPreSavePageAwareFilter(pageRepositoryMock)
	}

	void "attaches database entities when there are duplicates"() {
		given:
		Page nonPersistedPage = new Page(pageId: PAGE_ID)
		Page persistedPage = new Page(pageId: PAGE_ID)

		PageAware pageAware = Mock()
		pageAware.page >> nonPersistedPage
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware)

		when:
		List<PageAware> pageAwareListOutput = tolerantPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * pageRepositoryMock.findByPageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			Lists.newArrayList(persistedPage)
		}
		pageAwareListOutput[0].page == persistedPage
	}

	void "returns original collection when there is no duplicates"() {
		given:
		PageAware pageAware = Mock()
		pageAware.page >> new Page(pageId: PAGE_ID)
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware)

		when:
		List<PageAware> pageAwareListOutput = tolerantPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		1 * pageRepositoryMock.findByPageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			Lists.newArrayList()
		}
		pageAwareListOutput == pageAwareList
	}

}
