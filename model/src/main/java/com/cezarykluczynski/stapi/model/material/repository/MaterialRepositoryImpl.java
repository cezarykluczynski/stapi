package com.cezarykluczynski.stapi.model.material.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.material.entity.Material_;
import com.cezarykluczynski.stapi.model.material.query.MaterialQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialRepositoryImpl implements MaterialRepositoryCustom {

	private final MaterialQueryBuilderFactory materialQueryBuilderFactory;

	public MaterialRepositoryImpl(MaterialQueryBuilderFactory materialQueryBuilderFactory) {
		this.materialQueryBuilderFactory = materialQueryBuilderFactory;
	}

	@Override
	public Page<Material> findMatching(MaterialRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Material> materialQueryBuilder = materialQueryBuilderFactory.createQueryBuilder(pageable);

		materialQueryBuilder.equal(Material_.uid, criteria.getUid());
		materialQueryBuilder.like(Material_.name, criteria.getName());
		materialQueryBuilder.equal(Material_.chemicalCompound, criteria.getChemicalCompound());
		materialQueryBuilder.equal(Material_.biochemicalCompound, criteria.getBiochemicalCompound());
		materialQueryBuilder.equal(Material_.drug, criteria.getDrug());
		materialQueryBuilder.equal(Material_.poisonousSubstance, criteria.getPoisonousSubstance());
		materialQueryBuilder.equal(Material_.explosive, criteria.getExplosive());
		materialQueryBuilder.equal(Material_.gemstone, criteria.getGemstone());
		materialQueryBuilder.equal(Material_.alloyOrComposite, criteria.getAlloyOrComposite());
		materialQueryBuilder.equal(Material_.fuel, criteria.getFuel());
		materialQueryBuilder.equal(Material_.mineral, criteria.getMineral());
		materialQueryBuilder.equal(Material_.preciousMaterial, criteria.getPreciousMaterial());
		materialQueryBuilder.setSort(criteria.getSort());

		return materialQueryBuilder.findPage();
	}

}
