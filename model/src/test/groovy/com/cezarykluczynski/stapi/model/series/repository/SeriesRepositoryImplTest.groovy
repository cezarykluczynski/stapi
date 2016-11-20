package com.cezarykluczynski.stapi.model.series.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestOrderDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.query.SeriesQueryBuilderFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate

class SeriesRepositoryImplTest extends Specification {

	private static final String GUID = 'GUID'
	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'ABBREVIATION'
	private static final Integer PRODUCTION_START_YEAR_FROM = 1970
	private static final Integer PRODUCTION_START_YEAR_TO = 1995
	private static final Integer PRODUCTION_END_YEAR_FROM = 1980
	private static final Integer PRODUCTION_END_YEAR_TO = 2005
	private static final LocalDate ORIGINAL_RUN_START_FROM = LocalDate.of(1971, 1, 2)
	private static final LocalDate ORIGINAL_RUN_START_TO = LocalDate.of(1991, 3, 4)
	private static final LocalDate ORIGINAL_RUN_END_FROM = LocalDate.of(1998, 5, 6)
	private static final LocalDate ORIGINAL_RUN_END_TO = LocalDate.of(1999, 7, 8)
	private static final RequestOrderDTO ORDER = new RequestOrderDTO()

	private SeriesQueryBuilderFactory seriesQueryBuilderMock

	private SeriesRepositoryImpl seriesRepositoryImpl

	private QueryBuilder<Series> seriesQueryBuilder

	private Pageable pageable

	private SeriesRequestDTO seriesRequestDTO

	private Page page

	def setup() {
		seriesQueryBuilderMock = Mock(SeriesQueryBuilderFactory)
		seriesRepositoryImpl = new SeriesRepositoryImpl(seriesQueryBuilderMock)
		seriesQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		seriesRequestDTO = Mock(SeriesRequestDTO)
		page = Mock(Page)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = seriesRepositoryImpl.findMatching(seriesRequestDTO, pageable)

		then:
		1 * seriesQueryBuilderMock.createQueryBuilder(pageable) >> seriesQueryBuilder

		then: 'guid criteria is set'
		1 * seriesRequestDTO.getGuid() >> GUID
		1 * seriesQueryBuilder.equal("guid", GUID)

		then: 'string criteria are set'
		1 * seriesRequestDTO.getTitle() >> TITLE
		1 * seriesQueryBuilder.like("title", TITLE)
		1 * seriesRequestDTO.getAbbreviation() >> ABBREVIATION
		1 * seriesQueryBuilder.like("abbreviation", ABBREVIATION)

		then: 'date criteria are set'
		1 * seriesRequestDTO.getProductionStartYearFrom() >> PRODUCTION_START_YEAR_FROM
		1 * seriesRequestDTO.getProductionStartYearTo() >> PRODUCTION_START_YEAR_TO
		1 * seriesQueryBuilder.between("productionStartYear", PRODUCTION_START_YEAR_FROM, PRODUCTION_START_YEAR_TO)
		1 * seriesRequestDTO.getProductionEndYearFrom() >> PRODUCTION_END_YEAR_FROM
		1 * seriesRequestDTO.getProductionEndYearTo() >> PRODUCTION_END_YEAR_TO
		1 * seriesQueryBuilder.between("productionEndYear", PRODUCTION_END_YEAR_FROM, PRODUCTION_END_YEAR_TO)
		1 * seriesRequestDTO.getOriginalRunStartDateFrom() >> ORIGINAL_RUN_START_FROM
		1 * seriesRequestDTO.getOriginalRunStartDateTo() >> ORIGINAL_RUN_START_TO
		1 * seriesQueryBuilder.between("originalRunStartDate", ORIGINAL_RUN_START_FROM, ORIGINAL_RUN_START_TO)
		1 * seriesRequestDTO.getOriginalRunEndDateFrom() >> ORIGINAL_RUN_END_FROM
		1 * seriesRequestDTO.getOriginalRunEndDateTo() >> ORIGINAL_RUN_END_TO
		1 * seriesQueryBuilder.between("originalRunEndDate", ORIGINAL_RUN_END_FROM, ORIGINAL_RUN_END_TO)

		then: 'order is set'
		1 * seriesRequestDTO.getOrder() >> ORDER
		1 * seriesQueryBuilder.setOrder(ORDER)

		then: 'page is searched for and returned'
		1 * seriesQueryBuilder.findPage() >> page
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
