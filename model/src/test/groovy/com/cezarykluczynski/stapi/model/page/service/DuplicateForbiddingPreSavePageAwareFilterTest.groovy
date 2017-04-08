package com.cezarykluczynski.stapi.model.page.service

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.google.common.collect.Lists
import spock.lang.Specification

class DuplicateForbiddingPreSavePageAwareFilterTest extends Specification {

	private static final Long PAGE_ID = 1

	private PageBoundToEntityFilteringFinder pageBoundToEntityFilteringFinderMock

	private DuplicateForbiddingPreSavePageAwareFilter strictPreSavePageAwareProcessor

	void setup() {
		pageBoundToEntityFilteringFinderMock = Mock()
		strictPreSavePageAwareProcessor = new DuplicateForbiddingPreSavePageAwareFilter(pageBoundToEntityFilteringFinderMock)
	}

	void "throws exception when chunk contains entities with equal pageIds"() {
		given:
		PageAware pageAware = Mock(PageAware)
		pageAware.page >> new Page(pageId: PAGE_ID)
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware, pageAware)

		when:
		strictPreSavePageAwareProcessor.process(pageAwareList, null)

		then:
		RuntimeException ex = thrown(RuntimeException)
		ex.message.contains('Duplicated page entries in chunk')
	}

	void "throws exception when pages with given pageId already exists in database"() {
		given:
		PageAware pageAware = Mock(PageAware)
		pageAware.page >> new Page(pageId: PAGE_ID)
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware)

		when:
		strictPreSavePageAwareProcessor.process(pageAwareList, Performer)

		then:
		1 * pageBoundToEntityFilteringFinderMock.find(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			assert args[1] == Performer
			Lists.newArrayList(new Page(pageId: PAGE_ID))
		}
		RuntimeException ex = thrown(RuntimeException)
		ex.message.contains('Pages already persisted')
	}

	void "returns original collection when there is no duplicates"() {
		given:
		PageAware pageAware = Mock(PageAware)
		pageAware.page >> new Page(pageId: PAGE_ID)
		List<PageAware> pageAwareList = Lists.newArrayList(pageAware)

		when:
		List<PageAware> pageAwareListOutput = strictPreSavePageAwareProcessor.process(pageAwareList, Performer)

		then:
		1 * pageBoundToEntityFilteringFinderMock.find(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			assert args[1] == Performer
			Lists.newArrayList()
		}
		pageAwareListOutput == pageAwareList
	}

}
