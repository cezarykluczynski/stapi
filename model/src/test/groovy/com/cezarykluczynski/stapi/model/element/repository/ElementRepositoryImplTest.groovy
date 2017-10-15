package com.cezarykluczynski.stapi.model.element.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.model.element.entity.Element_
import com.cezarykluczynski.stapi.model.element.query.ElementQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractElementTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ElementRepositoryImplTest extends AbstractElementTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ElementQueryBuilderFactory elementQueryBuilderFactory

	private ElementRepositoryImpl elementRepositoryImpl

	private QueryBuilder<Element> elementQueryBuilder

	private Pageable pageable

	private ElementRequestDTO elementRequestDTO

	private Element element

	private Page page

	void setup() {
		elementQueryBuilderFactory = Mock()
		elementRepositoryImpl = new ElementRepositoryImpl(elementQueryBuilderFactory)
		elementQueryBuilder = Mock()
		pageable = Mock()
		elementRequestDTO = Mock()
		page = Mock()
		element = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = elementRepositoryImpl.findMatching(elementRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * elementQueryBuilderFactory.createQueryBuilder(pageable) >> elementQueryBuilder

		then: 'uid criteria is set'
		1 * elementRequestDTO.uid >> UID
		1 * elementQueryBuilder.equal(Element_.uid, UID)

		then: 'string criteria are set'
		1 * elementRequestDTO.name >> NAME
		1 * elementQueryBuilder.like(Element_.name, NAME)
		1 * elementRequestDTO.symbol >> SYMBOL
		1 * elementQueryBuilder.like(Element_.symbol, SYMBOL)

		then: 'boolean criteria are set'
		1 * elementRequestDTO.transuranium >> TRANSURANIUM
		1 * elementQueryBuilder.equal(Element_.transuranium, TRANSURANIUM)
		1 * elementRequestDTO.gammaSeries >> GAMMA_SERIES
		1 * elementQueryBuilder.equal(Element_.gammaSeries, GAMMA_SERIES)
		1 * elementRequestDTO.hypersonicSeries >> HYPERSONIC_SERIES
		1 * elementQueryBuilder.equal(Element_.hypersonicSeries, HYPERSONIC_SERIES)
		1 * elementRequestDTO.megaSeries >> MEGA_SERIES
		1 * elementQueryBuilder.equal(Element_.megaSeries, MEGA_SERIES)
		1 * elementRequestDTO.omegaSeries >> OMEGA_SERIES
		1 * elementQueryBuilder.equal(Element_.omegaSeries, OMEGA_SERIES)
		1 * elementRequestDTO.transonicSeries >> TRANSONIC_SERIES
		1 * elementQueryBuilder.equal(Element_.transonicSeries, TRANSONIC_SERIES)
		1 * elementRequestDTO.worldSeries >> WORLD_SERIES
		1 * elementQueryBuilder.equal(Element_.worldSeries, WORLD_SERIES)

		then: 'sort is set'
		1 * elementRequestDTO.sort >> SORT
		1 * elementQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * elementQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
