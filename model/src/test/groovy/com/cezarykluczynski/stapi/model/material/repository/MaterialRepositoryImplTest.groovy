package com.cezarykluczynski.stapi.model.material.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.model.material.entity.Material_
import com.cezarykluczynski.stapi.model.material.query.MaterialQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractMaterialTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class MaterialRepositoryImplTest extends AbstractMaterialTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MaterialQueryBuilderFactory materialQueryBuilderFactory

	private MaterialRepositoryImpl materialRepositoryImpl

	private QueryBuilder<Material> materialQueryBuilder

	private Pageable pageable

	private MaterialRequestDTO materialRequestDTO

	private Material material

	private Page page

	void setup() {
		materialQueryBuilderFactory = Mock()
		materialRepositoryImpl = new MaterialRepositoryImpl(materialQueryBuilderFactory)
		materialQueryBuilder = Mock()
		pageable = Mock()
		materialRequestDTO = Mock()
		page = Mock()
		material = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = materialRepositoryImpl.findMatching(materialRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * materialQueryBuilderFactory.createQueryBuilder(pageable) >> materialQueryBuilder

		then: 'uid criteria is set'
		1 * materialRequestDTO.uid >> UID
		1 * materialQueryBuilder.equal(Material_.uid, UID)

		then: 'string criteria are set'
		1 * materialRequestDTO.name >> NAME
		1 * materialQueryBuilder.like(Material_.name, NAME)

		then: 'boolean criteria are set'
		1 * materialRequestDTO.chemicalCompound >> CHEMICAL_COMPOUND
		1 * materialQueryBuilder.equal(Material_.chemicalCompound, CHEMICAL_COMPOUND)
		1 * materialRequestDTO.biochemicalCompound >> BIOCHEMICAL_COMPOUND
		1 * materialQueryBuilder.equal(Material_.biochemicalCompound, BIOCHEMICAL_COMPOUND)
		1 * materialRequestDTO.drug >> DRUG
		1 * materialQueryBuilder.equal(Material_.drug, DRUG)
		1 * materialRequestDTO.poisonousSubstance >> POISONOUS_SUBSTANCE
		1 * materialQueryBuilder.equal(Material_.poisonousSubstance, POISONOUS_SUBSTANCE)
		1 * materialRequestDTO.explosive >> EXPLOSIVE
		1 * materialQueryBuilder.equal(Material_.explosive, EXPLOSIVE)
		1 * materialRequestDTO.gemstone >> GEMSTONE
		1 * materialQueryBuilder.equal(Material_.gemstone, GEMSTONE)
		1 * materialRequestDTO.alloyOrComposite >> ALLOY_OR_COMPOSITE
		1 * materialQueryBuilder.equal(Material_.alloyOrComposite, ALLOY_OR_COMPOSITE)
		1 * materialRequestDTO.fuel >> FUEL
		1 * materialQueryBuilder.equal(Material_.fuel, FUEL)
		1 * materialRequestDTO.mineral >> MINERAL
		1 * materialQueryBuilder.equal(Material_.mineral, MINERAL)
		1 * materialRequestDTO.preciousMaterial >> PRECIOUS_MATERIAL
		1 * materialQueryBuilder.equal(Material_.preciousMaterial, PRECIOUS_MATERIAL)

		then: 'sort is set'
		1 * materialRequestDTO.sort >> SORT
		1 * materialQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * materialQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
