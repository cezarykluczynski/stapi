package com.cezarykluczynski.stapi.auth.api_key.mapper

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.util.wrapper.Pager
import org.apache.commons.lang3.RandomUtils
import org.springframework.data.domain.Page
import spock.lang.Specification

class PagerMapperTest extends Specification {

	private static final int TOTAL_PAGES = RandomUtils.nextInt()
	private static final int TOTAL_ELEMENTS = RandomUtils.nextInt()
	private static final int PAGE_NUMBER = RandomUtils.nextInt()
	private static final int PAGE_SIZE = RandomUtils.nextInt()
	private static final int NUMBER_OF_ELEMENTS_IN_PAGE = RandomUtils.nextInt()
	private static final IS_FIRST = RandomUtils.nextBoolean()
	private static final IS_LAST = RandomUtils.nextBoolean()
	private static final HAS_NEXT = RandomUtils.nextBoolean()
	private static final HAS_PREVIOUS = RandomUtils.nextBoolean()

	PagerMapper pagerMapper

	void setup() {
		pagerMapper = new PagerMapper()
	}

	@SuppressWarnings(['UnnecessaryGetter', 'UnnecessaryParenthesesForMethodCallWithClosure'])
	void "maps page to pager"() {
		given:
		Page<ApiKey> page = Mock() {
			getTotalPages() >> TOTAL_PAGES
			getTotalElements() >> TOTAL_ELEMENTS
			getNumber() >> PAGE_NUMBER
			getSize() >> PAGE_SIZE
			getNumberOfElements() >> NUMBER_OF_ELEMENTS_IN_PAGE
			isFirst() >> IS_FIRST
			isLast() >> IS_LAST
			hasNext() >> HAS_NEXT
			hasPrevious() >> HAS_PREVIOUS
		}

		when:
		Pager pager = pagerMapper.map(page)

		then:
		pager.totalPages == TOTAL_PAGES
		pager.totalElements ==  TOTAL_ELEMENTS
		pager.pageNumber == PAGE_NUMBER
		pager.pageSize == PAGE_SIZE
		pager.numberOfElementsInPage == NUMBER_OF_ELEMENTS_IN_PAGE
		pager.first == IS_FIRST
		pager.last == IS_LAST
		pager.hasNext == HAS_NEXT
		pager.hasPrevious == HAS_PREVIOUS
	}

}
