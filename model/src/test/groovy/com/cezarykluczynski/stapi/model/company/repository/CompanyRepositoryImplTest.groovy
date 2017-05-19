package com.cezarykluczynski.stapi.model.company.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.entity.Company_
import com.cezarykluczynski.stapi.model.company.query.CompanyQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractCompanyTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class CompanyRepositoryImplTest extends AbstractCompanyTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private CompanyQueryBuilderFactory companyQueryBuilderFactory

	private CompanyRepositoryImpl companyRepositoryImpl

	private QueryBuilder<Company> companyQueryBuilder

	private Pageable pageable

	private CompanyRequestDTO companyRequestDTO

	private Company company

	private Page page

	void setup() {
		companyQueryBuilderFactory = Mock()
		companyRepositoryImpl = new CompanyRepositoryImpl(companyQueryBuilderFactory)
		companyQueryBuilder = Mock()
		pageable = Mock()
		companyRequestDTO = Mock()
		page = Mock()
		company = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = companyRepositoryImpl.findMatching(companyRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * companyQueryBuilderFactory.createQueryBuilder(pageable) >> companyQueryBuilder

		then: 'uid criteria is set'
		1 * companyRequestDTO.uid >> UID
		1 * companyQueryBuilder.equal(Company_.uid, UID)

		then: 'string criteria are set'
		1 * companyRequestDTO.name >> NAME
		1 * companyQueryBuilder.like(Company_.name, NAME)

		then: 'boolean criteria are set'
		1 * companyRequestDTO.broadcaster >> BROADCASTER
		1 * companyQueryBuilder.equal(Company_.broadcaster, BROADCASTER)
		1 * companyRequestDTO.collectibleCompany >> COLLECTIBLE_COMPANY
		1 * companyQueryBuilder.equal(Company_.collectibleCompany, COLLECTIBLE_COMPANY)
		1 * companyRequestDTO.conglomerate >> CONGLOMERATE
		1 * companyQueryBuilder.equal(Company_.conglomerate, CONGLOMERATE)
		1 * companyRequestDTO.digitalVisualEffectsCompany >> DIGITAL_VISUAL_EFFECTS_COMPANY
		1 * companyQueryBuilder.equal(Company_.digitalVisualEffectsCompany, DIGITAL_VISUAL_EFFECTS_COMPANY)
		1 * companyRequestDTO.distributor >> DISTRIBUTOR
		1 * companyQueryBuilder.equal(Company_.distributor, DISTRIBUTOR)
		1 * companyRequestDTO.gameCompany >> GAME_COMPANY
		1 * companyQueryBuilder.equal(Company_.gameCompany, GAME_COMPANY)
		1 * companyRequestDTO.filmEquipmentCompany >> FILM_EQUIPMENT_COMPANY
		1 * companyQueryBuilder.equal(Company_.filmEquipmentCompany, FILM_EQUIPMENT_COMPANY)
		1 * companyRequestDTO.makeUpEffectsStudio >> MAKE_UP_EFFECTS_STUDIO
		1 * companyQueryBuilder.equal(Company_.makeUpEffectsStudio, MAKE_UP_EFFECTS_STUDIO)
		1 * companyRequestDTO.mattePaintingCompany >> MATTE_PAINTING_COMPANY
		1 * companyQueryBuilder.equal(Company_.mattePaintingCompany, MATTE_PAINTING_COMPANY)
		1 * companyRequestDTO.modelAndMiniatureEffectsCompany >> MODEL_AND_MINIATURE_EFFECTS_COMPANY
		1 * companyQueryBuilder.equal(Company_.modelAndMiniatureEffectsCompany, MODEL_AND_MINIATURE_EFFECTS_COMPANY)
		1 * companyRequestDTO.postProductionCompany >> POST_PRODUCTION_COMPANY
		1 * companyQueryBuilder.equal(Company_.postProductionCompany, POST_PRODUCTION_COMPANY)
		1 * companyRequestDTO.productionCompany >> PRODUCTION_COMPANY
		1 * companyQueryBuilder.equal(Company_.productionCompany, PRODUCTION_COMPANY)
		1 * companyRequestDTO.propCompany >> PROP_COMPANY
		1 * companyQueryBuilder.equal(Company_.propCompany, PROP_COMPANY)
		1 * companyRequestDTO.recordLabel >> RECORD_LABEL
		1 * companyQueryBuilder.equal(Company_.recordLabel, RECORD_LABEL)
		1 * companyRequestDTO.specialEffectsCompany >> SPECIAL_EFFECTS_COMPANY
		1 * companyQueryBuilder.equal(Company_.specialEffectsCompany, SPECIAL_EFFECTS_COMPANY)
		1 * companyRequestDTO.tvAndFilmProductionCompany >> TV_AND_FILM_PRODUCTION_COMPANY
		1 * companyQueryBuilder.equal(Company_.tvAndFilmProductionCompany, TV_AND_FILM_PRODUCTION_COMPANY)
		1 * companyRequestDTO.videoGameCompany >> VIDEO_GAME_COMPANY
		1 * companyQueryBuilder.equal(Company_.videoGameCompany, VIDEO_GAME_COMPANY)

		then: 'sort is set'
		1 * companyRequestDTO.sort >> SORT
		1 * companyQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * companyQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
