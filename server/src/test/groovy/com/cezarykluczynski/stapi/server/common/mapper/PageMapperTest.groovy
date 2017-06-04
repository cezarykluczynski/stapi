package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage as RESTResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage as SOAPResponsePage
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.util.PageDefault
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

	void setup() {
		pageMapper = new PageMapper()
	}

	void "maps Page to SOAP ResponsePage"() {
		given:
		Page page = Mock()
		page.number >> PAGE_NUMBER
		page.size >> PAGE_SIZE
		page.totalElements >> TOTAL_ELEMENTS
		page.totalPages >> TOTAL_PAGES
		page.numberOfElements >> NUMBER_OF_ELEMENTS
		page.isFirst() >> IS_FIRST_PAGE
		page.isLast() >> IS_LAST_PAGE

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

	void "maps Page to REST ResponsePage"() {
		given:
		Page page = Mock()
		page.number >> PAGE_NUMBER
		page.size >> PAGE_SIZE
		page.totalElements >> TOTAL_ELEMENTS
		page.totalPages >> TOTAL_PAGES
		page.numberOfElements >> NUMBER_OF_ELEMENTS
		page.isFirst() >> IS_FIRST_PAGE
		page.isLast() >> IS_LAST_PAGE

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

	void "maps RequestPage to PageRequest"() {
		given:
		RequestPage requestPage = new RequestPage(pageNumber: PAGE_NUMBER, pageSize: PAGE_SIZE)

		when:
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest requestPage

		then:
		pageRequest.pageNumber == PAGE_NUMBER
		pageRequest.pageSize == PAGE_SIZE
	}

	void "ensures that ranges are respected when mapping from RequestPage"() {
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

	void "maps RequestPage with null values to PageRequest with default values"() {
		when:
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest new RequestPage()

		then:
		pageRequest.pageNumber == PageDefault.PAGE_NUMBER
		pageRequest.pageSize == PageDefault.PAGE_SIZE
	}

	void "maps null RequestPage to default PageRequest"() {
		when:
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest null

		then:
		pageRequest == PageDefault.PAGE_REQUEST
	}

	void "maps PageAwareBeanParams to PageRequest"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE

		when:
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest pageAwareBeanParams

		then:
		pageRequest.pageNumber == PAGE_NUMBER
		pageRequest.pageSize == PAGE_SIZE
	}

	void "ensures that ranges are respected when mapping from PageSortBeanParams"() {
		given:
		PageSortBeanParams pageSortBeanParams1 = Mock()
		pageSortBeanParams1.pageNumber >> -1
		PageSortBeanParams pageSortBeanParams2 = Mock()
		pageSortBeanParams2.pageSize >> -1
		PageSortBeanParams pageSortBeanParams3 = Mock()
		pageSortBeanParams3.pageSize >> 10000

		when:
		PageRequest pageRequestWithTooLowPageNumber = pageMapper.fromPageSortBeanParamsToPageRequest pageSortBeanParams1

		then:
		pageRequestWithTooLowPageNumber.pageNumber == PageDefault.PAGE_NUMBER

		when:
		PageRequest pageRequestWithTooLowPageSize = pageMapper.fromPageSortBeanParamsToPageRequest pageSortBeanParams2

		then:
		pageRequestWithTooLowPageSize.pageSize == PageDefault.PAGE_SIZE

		when:
		PageRequest pageRequestWithTooHighPageSize = pageMapper.fromPageSortBeanParamsToPageRequest pageSortBeanParams3

		then:
		pageRequestWithTooHighPageSize.pageSize == PageDefault.PAGE_SIZE_MAX
	}

	void "maps PageAwareBeanParams with null values to PageRequest with default values"() {
		given:
		PageSortBeanParams pageSortBeanParams = Mock()

		when:
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest pageSortBeanParams

		then:
		pageRequest.pageNumber == PageDefault.PAGE_NUMBER
		pageRequest.pageSize == PageDefault.PAGE_SIZE
	}

	void "maps null PageAwareBeanParams to default PageRequest"() {
		when:
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest null

		then:
		pageRequest == PageDefault.PAGE_REQUEST
	}

	void "gets default PageRequest"() {
		when:
		PageRequest pageRequest = pageMapper.defaultPageRequest

		then:
		pageRequest == PageDefault.PAGE_REQUEST
	}

}
