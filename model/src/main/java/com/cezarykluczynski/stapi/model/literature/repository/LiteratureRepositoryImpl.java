package com.cezarykluczynski.stapi.model.literature.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.model.literature.entity.Literature_;
import com.cezarykluczynski.stapi.model.literature.query.LiteratureQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class LiteratureRepositoryImpl implements LiteratureRepositoryCustom {

	private final LiteratureQueryBuilderFactory literatureQueryBuilderFactory;

	public LiteratureRepositoryImpl(LiteratureQueryBuilderFactory literatureQueryBuilderFactory) {
		this.literatureQueryBuilderFactory = literatureQueryBuilderFactory;
	}

	@Override
	public Page<Literature> findMatching(LiteratureRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Literature> literatureQueryBuilder = literatureQueryBuilderFactory.createQueryBuilder(pageable);

		literatureQueryBuilder.equal(Literature_.uid, criteria.getUid());
		literatureQueryBuilder.like(Literature_.title, criteria.getTitle());
		literatureQueryBuilder.equal(Literature_.earthlyOrigin, criteria.getEarthlyOrigin());
		literatureQueryBuilder.equal(Literature_.shakespeareanWork, criteria.getShakespeareanWork());
		literatureQueryBuilder.equal(Literature_.report, criteria.getReport());
		literatureQueryBuilder.equal(Literature_.scientificLiterature, criteria.getScientificLiterature());
		literatureQueryBuilder.equal(Literature_.technicalManual, criteria.getTechnicalManual());
		literatureQueryBuilder.equal(Literature_.religiousLiterature, criteria.getReligiousLiterature());
		literatureQueryBuilder.setSort(criteria.getSort());

		return literatureQueryBuilder.findPage();
	}

}
