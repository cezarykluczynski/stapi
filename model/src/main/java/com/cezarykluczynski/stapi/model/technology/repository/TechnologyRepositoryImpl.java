package com.cezarykluczynski.stapi.model.technology.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.technology.entity.Technology_;
import com.cezarykluczynski.stapi.model.technology.query.TechnologyQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TechnologyRepositoryImpl implements TechnologyRepositoryCustom {

	private final TechnologyQueryBuilderFactory technologyQueryBuilderFactory;

	public TechnologyRepositoryImpl(TechnologyQueryBuilderFactory technologyQueryBuilderFactory) {
		this.technologyQueryBuilderFactory = technologyQueryBuilderFactory;
	}

	@Override
	public Page<Technology> findMatching(TechnologyRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Technology> technologyQueryBuilder = technologyQueryBuilderFactory.createQueryBuilder(pageable);

		technologyQueryBuilder.equal(Technology_.uid, criteria.getUid());
		technologyQueryBuilder.like(Technology_.name, criteria.getName());
		technologyQueryBuilder.equal(Technology_.borgTechnology, criteria.getBorgTechnology());
		technologyQueryBuilder.equal(Technology_.borgComponent, criteria.getBorgComponent());
		technologyQueryBuilder.equal(Technology_.communicationsTechnology, criteria.getCommunicationsTechnology());
		technologyQueryBuilder.equal(Technology_.computerTechnology, criteria.getComputerTechnology());
		technologyQueryBuilder.equal(Technology_.computerProgramming, criteria.getComputerProgramming());
		technologyQueryBuilder.equal(Technology_.subroutine, criteria.getSubroutine());
		technologyQueryBuilder.equal(Technology_.database, criteria.getDatabase());
		technologyQueryBuilder.equal(Technology_.energyTechnology, criteria.getEnergyTechnology());
		technologyQueryBuilder.equal(Technology_.fictionalTechnology, criteria.getFictionalTechnology());
		technologyQueryBuilder.equal(Technology_.holographicTechnology, criteria.getHolographicTechnology());
		technologyQueryBuilder.equal(Technology_.identificationTechnology, criteria.getIdentificationTechnology());
		technologyQueryBuilder.equal(Technology_.lifeSupportTechnology, criteria.getLifeSupportTechnology());
		technologyQueryBuilder.equal(Technology_.sensorTechnology, criteria.getSensorTechnology());
		technologyQueryBuilder.equal(Technology_.shieldTechnology, criteria.getShieldTechnology());
		technologyQueryBuilder.equal(Technology_.tool, criteria.getTool());
		technologyQueryBuilder.equal(Technology_.culinaryTool, criteria.getCulinaryTool());
		technologyQueryBuilder.equal(Technology_.engineeringTool, criteria.getEngineeringTool());
		technologyQueryBuilder.equal(Technology_.householdTool, criteria.getHouseholdTool());
		technologyQueryBuilder.equal(Technology_.medicalEquipment, criteria.getMedicalEquipment());
		technologyQueryBuilder.equal(Technology_.transporterTechnology, criteria.getTransporterTechnology());
		technologyQueryBuilder.setSort(criteria.getSort());

		return technologyQueryBuilder.findPage();
	}

}
