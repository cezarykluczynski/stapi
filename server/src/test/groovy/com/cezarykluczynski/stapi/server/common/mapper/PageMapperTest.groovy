package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.soap.RequestPage
import com.cezarykluczynski.stapi.client.soap.ResponsePage
import com.cezarykluczynski.stapi.server.util.PageDefault
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PageMapperTest extends Specification {

	private static final int PAGE_NUMBER = 3
	private static final int PAGE_SIZE = 50
	private static final Long TOTAL_ELEMENTS = 195
	private static final int TOTAL_PAGES = 5
	private static final int NUMBER_OF_ELEMENTS = 45
	private static final boolean IS_FIRST_PAGE = true
	private static final boolean IS_LAST_PAGE = true

	private PageMapper pageMapper

	def setup() {
		pageMapper = Mappers.getMapper(PageMapper.class)
	}

	def "maps Page to ResponsePage"() {
		given:
		Page page = Mock(Page) {
			getNumber() >> PAGE_NUMBER
			getSize() >> PAGE_SIZE
			getTotalElements() >> TOTAL_ELEMENTS
			getTotalPages() >> TOTAL_PAGES
			getNumberOfElements() >> NUMBER_OF_ELEMENTS
			isFirst() >> IS_FIRST_PAGE
			isLast() >> IS_LAST_PAGE
		}

		when:
		ResponsePage responsePage = pageMapper.toResponsePage(page)

		then:
		responsePage.pageNumber == PAGE_NUMBER
		responsePage.pageSize == PAGE_SIZE
		responsePage.totalElements == TOTAL_ELEMENTS.intValue()
		responsePage.totalPages == TOTAL_PAGES
		responsePage.numberOfElements == NUMBER_OF_ELEMENTS
		responsePage.firstPage == IS_FIRST_PAGE
		responsePage.lastPage == IS_LAST_PAGE
	}

	def "maps null to default PageRequest"() {
		when:
		PageRequest pageRequest = pageMapper.toPageRequest null

		then:
		pageRequest == PageDefault.PAGE_REQUEST
	}

	def "maps RequestPage to PageRequest"() {
		given:
		RequestPage requestPage = new RequestPage(pageNumber: PAGE_NUMBER, pageSize: PAGE_SIZE)

		when:
		PageRequest pageRequest = pageMapper.toPageRequest requestPage

		then:
		pageRequest.pageNumber == PAGE_NUMBER
		pageRequest.pageSize == PAGE_SIZE
	}

}
