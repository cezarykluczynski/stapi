package com.cezarykluczynski.stapi.model.common.repository

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.google.common.collect.Lists
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class InPageAwareRepositoryPageFinderTest extends Specification {

	private static final Long PAGE_ID_1 = 1L
	private static final Long PAGE_ID_2 = 2L
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageAwareQueryBuilderSingletonFactoryProducer pageAwareQueryBuilderSingletonFactoryProducerMock

	PageAwareQueryBuilderFactory pageAwareQueryBuilderFactoryMock

	private InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinder

	private QueryBuilder<PageAware> pageAwareQueryBuilderMock

	private Set<Long> pageIds

	void setup() {
		pageAwareQueryBuilderSingletonFactoryProducerMock = Mock()
		pageAwareQueryBuilderFactoryMock = Mock()
		pageAwareQueryBuilderMock = Mock()
		inPageAwareRepositoryPageFinder = new InPageAwareRepositoryPageFinder(pageAwareQueryBuilderSingletonFactoryProducerMock)
	}

	void "returns empty list if no entities were found"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList()

		when:
		List<Page> pageList = inPageAwareRepositoryPageFinder.findByPagePageIdIn(pageIds, Performer)

		then:
		1 * pageAwareQueryBuilderSingletonFactoryProducerMock.create(Performer) >> pageAwareQueryBuilderFactoryMock
		1 * pageAwareQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == 100
			pageAwareQueryBuilderMock
		}
		1 * pageAwareQueryBuilderMock.joinPageIdsIn(pageIds)
		1 * pageAwareQueryBuilderMock.joinEquals('page', 'mediaWikiSource', MEDIA_WIKI_SOURCE, Page)
		1 * pageAwareQueryBuilderMock.findAll() >> pageAwareList
		pageList.empty
	}

	void "finds entities by page ids"() {
		given:
		Page page1 = Mock()
		page1.pageId >> PAGE_ID_1
		Page page2 = Mock()
		page2.pageId >> PAGE_ID_2
		PageAware pageAware1 = Mock()
		pageAware1.page >> page1
		PageAware pageAware2 = Mock()
		pageAware2.page >> page2

		List<PageAware> pageAwareList = Lists.newArrayList(pageAware1, pageAware2)

		when:
		List<Page> pageList = inPageAwareRepositoryPageFinder.findByPagePageIdIn(pageIds, Performer)

		then:
		1 * pageAwareQueryBuilderSingletonFactoryProducerMock.create(Performer) >> pageAwareQueryBuilderFactoryMock
		1 * pageAwareQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == 100
			pageAwareQueryBuilderMock
		}
		1 * pageAwareQueryBuilderMock.joinPageIdsIn(pageIds)
		1 * pageAwareQueryBuilderMock.joinEquals('page', 'mediaWikiSource', MEDIA_WIKI_SOURCE, Page)
		1 * pageAwareQueryBuilderMock.findAll() >> pageAwareList
		pageList.size() == 2
		pageList[0] == page1
		pageList[1] == page2
	}

}
