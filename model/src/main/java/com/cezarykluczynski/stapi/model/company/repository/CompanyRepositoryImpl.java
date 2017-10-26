package com.cezarykluczynski.stapi.model.company.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.entity.Company_;
import com.cezarykluczynski.stapi.model.company.query.CompanyQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

	private final CompanyQueryBuilderFactory companyQueryBuilderFactory;

	public CompanyRepositoryImpl(CompanyQueryBuilderFactory companyQueryBuilderFactory) {
		this.companyQueryBuilderFactory = companyQueryBuilderFactory;
	}

	@Override
	public Page<Company> findMatching(CompanyRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Company> companyQueryBuilder = companyQueryBuilderFactory.createQueryBuilder(pageable);

		companyQueryBuilder.equal(Company_.uid, criteria.getUid());
		companyQueryBuilder.like(Company_.name, criteria.getName());
		companyQueryBuilder.equal(Company_.broadcaster, criteria.getBroadcaster());
		companyQueryBuilder.equal(Company_.collectibleCompany, criteria.getCollectibleCompany());
		companyQueryBuilder.equal(Company_.conglomerate, criteria.getConglomerate());
		companyQueryBuilder.equal(Company_.digitalVisualEffectsCompany, criteria.getDigitalVisualEffectsCompany());
		companyQueryBuilder.equal(Company_.distributor, criteria.getDistributor());
		companyQueryBuilder.equal(Company_.gameCompany, criteria.getGameCompany());
		companyQueryBuilder.equal(Company_.filmEquipmentCompany, criteria.getFilmEquipmentCompany());
		companyQueryBuilder.equal(Company_.makeUpEffectsStudio, criteria.getMakeUpEffectsStudio());
		companyQueryBuilder.equal(Company_.mattePaintingCompany, criteria.getMattePaintingCompany());
		companyQueryBuilder.equal(Company_.modelAndMiniatureEffectsCompany, criteria.getModelAndMiniatureEffectsCompany());
		companyQueryBuilder.equal(Company_.postProductionCompany, criteria.getPostProductionCompany());
		companyQueryBuilder.equal(Company_.productionCompany, criteria.getProductionCompany());
		companyQueryBuilder.equal(Company_.propCompany, criteria.getPropCompany());
		companyQueryBuilder.equal(Company_.recordLabel, criteria.getRecordLabel());
		companyQueryBuilder.equal(Company_.specialEffectsCompany, criteria.getSpecialEffectsCompany());
		companyQueryBuilder.equal(Company_.tvAndFilmProductionCompany, criteria.getTvAndFilmProductionCompany());
		companyQueryBuilder.equal(Company_.videoGameCompany, criteria.getVideoGameCompany());
		companyQueryBuilder.setSort(criteria.getSort());

		return companyQueryBuilder.findPage();
	}

}
