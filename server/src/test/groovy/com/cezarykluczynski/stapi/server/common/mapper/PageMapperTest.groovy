package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage as SOAPResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage as RESTResponsePage
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
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
		pageMapper = Mappers.getMapper(PageMapper)
	}

	def "maps Page to SOAP ResponsePage"() {
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
		SOAPResponsePage responsePage = pageMapper.fromPageToSoapResponsePage(page)

		then:
		responsePage.pageNumber == PAGE_NUMBER
		responsePage.pageSize == PAGE_SIZE
		responsePage.totalElements == TOTAL_ELEMENTS.intValue()
		responsePage.totalPages == TOTAL_PAGES
		responsePage.numberOfElements == NUMBER_OF_ELEMENTS
		responsePage.firstPage == IS_FIRST_PAGE
		responsePage.lastPage == IS_LAST_PAGE
	}

	def "maps Page to REST ResponsePage"() {
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
		RESTResponsePage responsePage = pageMapper.fromPageToRestResponsePage(page)

		then:
		responsePage.pageNumber == PAGE_NUMBER
		responsePage.pageSize == PAGE_SIZE
		responsePage.totalElements == TOTAL_ELEMENTS.intValue()
		responsePage.totalPages == TOTAL_PAGES
		responsePage.numberOfElements == NUMBER_OF_ELEMENTS
		responsePage.firstPage == IS_FIRST_PAGE
		responsePage.lastPage == IS_LAST_PAGE
	}

	def "maps RequestPage to PageRequest"() {
		given:
		RequestPage requestPage = new RequestPage(pageNumber: PAGE_NUMBER, pageSize: PAGE_SIZE)

		when:
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest requestPage

		then:
		pageRequest.pageNumber == PAGE_NUMBER
		pageRequest.pageSize == PAGE_SIZE
	}

	def "ensures that ranges are respected when mapping from RequestPage"() {
		when:
		PageRequest requestPageWithTooLowPageNumber = pageMapper.fromRequestPageToPageRequest new RequestPage(pageNumber: -1)

		then:
		requestPageWithTooLowPageNumber.pageNumber == PageDefault.PAGE_NUMBER

		when:
		PageRequest pageRequestWithTooLowPageSize =  pageMapper.fromRequestPageToPageRequest new RequestPage(pageSize: -1)

		then:
		pageRequestWithTooLowPageSize.pageSize == PageDefault.PAGE_SIZE

		when:
		PageRequest pageRequestWithTooHighPageSize =  pageMapper.fromRequestPageToPageRequest new RequestPage(pageSize: 10000)

		then:
		pageRequestWithTooHighPageSize.pageSize == PageDefault.PAGE_SIZE_MAX
	}

	def "maps RequestPage with null values to PageRequest with default values"() {
		when:
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest new RequestPage()

		then:
		pageRequest.pageNumber == PageDefault.PAGE_NUMBER
		pageRequest.pageSize == PageDefault.PAGE_SIZE
	}

	def "maps null RequestPage to default PageRequest"() {
		when:
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest null

		then:
		pageRequest == PageDefault.PAGE_REQUEST
	}

	def "maps PageAwareBeanParams to PageRequest"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams) {
			getPageNumber() >> PAGE_NUMBER
			getPageSize() >> PAGE_SIZE
		}

		when:
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest pageAwareBeanParams

		then:
		pageRequest.pageNumber == PAGE_NUMBER
		pageRequest.pageSize == PAGE_SIZE
	}

	def "ensures that ranges are respected when mapping from PageSortBeanParams"() {
		when:
		PageRequest pageRequestWithTooLowPageNumber = pageMapper.fromPageSortBeanParamsToPageRequest Mock(PageSortBeanParams) {
			getPageNumber() >> -1
		}

		then:
		pageRequestWithTooLowPageNumber.pageNumber == PageDefault.PAGE_NUMBER

		when:
		PageRequest pageRequestWithTooLowPageSize = pageMapper.fromPageSortBeanParamsToPageRequest Mock(PageSortBeanParams) {
			getPageSize() >> -1
		}

		then:
		pageRequestWithTooLowPageSize.pageSize == PageDefault.PAGE_SIZE

		when:
		PageRequest pageRequestWithTooHighPageSize = pageMapper.fromPageSortBeanParamsToPageRequest Mock(PageSortBeanParams) {
			getPageSize() >> 10000
		}

		then:
		pageRequestWithTooHighPageSize.pageSize == PageDefault.PAGE_SIZE_MAX
	}

	def "maps PageAwareBeanParams with null values to PageRequest with default values"() {
		when:
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest Mock(PageSortBeanParams)

		then:
		pageRequest.pageNumber == PageDefault.PAGE_NUMBER
		pageRequest.pageSize == PageDefault.PAGE_SIZE
	}

	def "maps null PageAwareBeanParams to default PageRequest"() {
		when:
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest null

		then:
		pageRequest == PageDefault.PAGE_REQUEST
	}

}
