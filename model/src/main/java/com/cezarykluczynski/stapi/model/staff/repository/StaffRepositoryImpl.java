package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.query.StaffQueryBuiler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository
public class StaffRepositoryImpl implements StaffRepositoryCustom {

	private StaffQueryBuiler performerQueryBuiler;

	@Inject
	public StaffRepositoryImpl(StaffQueryBuiler performerQueryBuiler) {
		this.performerQueryBuiler = performerQueryBuiler;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Staff> findMatching(StaffRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Staff> performerQueryBuilder = performerQueryBuiler.createQueryBuilder(pageable);

		performerQueryBuilder.like("name", criteria.getName());
		performerQueryBuilder.like("birthName", criteria.getBirthName());
		performerQueryBuilder.like("placeOfBirth", criteria.getPlaceOfBirth());
		performerQueryBuilder.like("placeOfDeath", criteria.getPlaceOfDeath());
		performerQueryBuilder.between("dateOfBirth", criteria.getDateOfBirthFrom(),
				criteria.getDateOfBirthTo());
		performerQueryBuilder.between("dateOfDeath", criteria.getDateOfDeathFrom(),
				criteria.getDateOfDeathTo());
		performerQueryBuilder.equal("gender", criteria.getGender());

		return performerQueryBuilder.findPage();
	}

}
