package com.cezarykluczynski.stapi.model.literature.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.model.literature.entity.Literature_
import com.cezarykluczynski.stapi.model.literature.query.LiteratureQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractLiteratureTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class LiteratureRepositoryImplTest extends AbstractLiteratureTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private LiteratureQueryBuilderFactory literatureQueryBuilderFactory

	private LiteratureRepositoryImpl literatureRepositoryImpl

	private QueryBuilder<Literature> literatureQueryBuilder

	private Pageable pageable

	private LiteratureRequestDTO literatureRequestDTO

	private Page page

	void setup() {
		literatureQueryBuilderFactory = Mock()
		literatureRepositoryImpl = new LiteratureRepositoryImpl(literatureQueryBuilderFactory)
		literatureQueryBuilder = Mock()
		pageable = Mock()
		literatureRequestDTO = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = literatureRepositoryImpl.findMatching(literatureRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * literatureQueryBuilderFactory.createQueryBuilder(pageable) >> literatureQueryBuilder

		then: 'uid criteria is set'
		1 * literatureRequestDTO.uid >> UID
		1 * literatureQueryBuilder.equal(Literature_.uid, UID)

		then: 'string criteria are set'
		1 * literatureRequestDTO.title >> TITLE
		1 * literatureQueryBuilder.like(Literature_.title, TITLE)

		then: 'boolean criteria are set'
		1 * literatureRequestDTO.earthlyOrigin >> EARTHLY_ORIGIN
		1 * literatureQueryBuilder.equal(Literature_.earthlyOrigin, EARTHLY_ORIGIN)
		1 * literatureRequestDTO.shakespeareanWork >> SHAKESPEAREAN_WORK
		1 * literatureQueryBuilder.equal(Literature_.shakespeareanWork, SHAKESPEAREAN_WORK)
		1 * literatureRequestDTO.report >> REPORT
		1 * literatureQueryBuilder.equal(Literature_.report, REPORT)
		1 * literatureRequestDTO.scientificLiterature >> SCIENTIFIC_LITERATURE
		1 * literatureQueryBuilder.equal(Literature_.scientificLiterature, SCIENTIFIC_LITERATURE)
		1 * literatureRequestDTO.technicalManual >> TECHNICAL_MANUAL
		1 * literatureQueryBuilder.equal(Literature_.technicalManual, TECHNICAL_MANUAL)
		1 * literatureRequestDTO.religiousLiterature >> RELIGIOUS_LITERATURE
		1 * literatureQueryBuilder.equal(Literature_.religiousLiterature, RELIGIOUS_LITERATURE)

		then: 'sort is set'
		1 * literatureRequestDTO.sort >> SORT
		1 * literatureQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * literatureQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
