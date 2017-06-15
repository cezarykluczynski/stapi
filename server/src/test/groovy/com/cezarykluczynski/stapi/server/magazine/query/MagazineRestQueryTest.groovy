package com.cezarykluczynski.stapi.server.magazine.query

import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MagazineRestQueryTest extends Specification {

	private MagazineBaseRestMapper magazineBaseRestMapperMock

	private PageMapper pageMapperMock

	private MagazineRepository magazineRepositoryMock

	private MagazineRestQuery magazineRestQuery

	void setup() {
		magazineBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		magazineRepositoryMock = Mock()
		magazineRestQuery = new MagazineRestQuery(magazineBaseRestMapperMock, pageMapperMock, magazineRepositoryMock)
	}

	void "maps MagazineRestBeanParams to MagazineRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MagazineRestBeanParams magazineRestBeanParams = Mock()
		MagazineRequestDTO magazineRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = magazineRestQuery.query(magazineRestBeanParams)

		then:
		1 * magazineBaseRestMapperMock.mapBase(magazineRestBeanParams) >> magazineRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(magazineRestBeanParams) >> pageRequest
		1 * magazineRepositoryMock.findMatching(magazineRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
