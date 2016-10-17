package com.cezarykluczynski.stapi.model.page.service

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.repository.PageRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class DuplicateForbiddingSavePageAwareProcessorTest extends Specification {

	private static final Long PAGE_ID = 1

	private PageRepository pageRepositoryMock

	private DuplicateForbiddingSavePageAwareProcessor strictPreSavePageAwareProcessor

	def setup() {
		pageRepositoryMock = Mock(PageRepository)
		strictPreSavePageAwareProcessor = new DuplicateForbiddingSavePageAwareProcessor(pageRepositoryMock)
	}

	def "throws exception when chunk contains entities with equal pageIds"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID)
				},
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID)
				}
		)

		when:
		strictPreSavePageAwareProcessor.process(pageAwareList)

		then:
		RuntimeException ex = thrown(RuntimeException)
		ex.message.contains('Duplicated page entries in chunk')
	}

	def "throws exception when pages with given pageId already exists in database"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID)
				}
		)

		when:
		strictPreSavePageAwareProcessor.process(pageAwareList)

		then:
		1 * pageRepositoryMock.findByPageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			return Lists.newArrayList(new Page(pageId: PAGE_ID))
		}
		RuntimeException ex = thrown(RuntimeException)
		ex.message.contains('Pages already persisted')
	}

	def "returns original collection when there is no duplicates"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> new Page(pageId: PAGE_ID)
				}
		)

		when:
		List<PageAware> pageAwareListOutput = strictPreSavePageAwareProcessor.process(pageAwareList)

		then:
		1 * pageRepositoryMock.findByPageIdIn(*_) >> { args ->
			assert ((Collection<Long>) args[0]).contains(PAGE_ID)
			return Lists.newArrayList()
		}
		pageAwareListOutput == pageAwareList
	}

}
