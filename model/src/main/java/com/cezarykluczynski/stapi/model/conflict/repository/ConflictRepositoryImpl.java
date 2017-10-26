package com.cezarykluczynski.stapi.model.conflict.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict_;
import com.cezarykluczynski.stapi.model.conflict.query.ConflictQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ConflictRepositoryImpl extends AbstractRepositoryImpl<Conflict> implements ConflictRepositoryCustom {

	private final ConflictQueryBuilderFactory conflictQueryBuilderFactory;

	public ConflictRepositoryImpl(ConflictQueryBuilderFactory conflictQueryBuilderFactory) {
		this.conflictQueryBuilderFactory = conflictQueryBuilderFactory;
	}

	@Override
	public Page<Conflict> findMatching(ConflictRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Conflict> conflictQueryBuilder = conflictQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		conflictQueryBuilder.equal(Conflict_.uid, uid);
		conflictQueryBuilder.like(Conflict_.name, criteria.getName());
		conflictQueryBuilder.between(Conflict_.yearFrom, criteria.getYearFrom(), null);
		conflictQueryBuilder.between(Conflict_.yearTo, null, criteria.getYearTo());
		conflictQueryBuilder.equal(Conflict_.earthConflict, criteria.getEarthConflict());
		conflictQueryBuilder.equal(Conflict_.federationWar, criteria.getFederationWar());
		conflictQueryBuilder.equal(Conflict_.klingonWar, criteria.getKlingonWar());
		conflictQueryBuilder.equal(Conflict_.dominionWarBattle, criteria.getDominionWarBattle());
		conflictQueryBuilder.equal(Conflict_.alternateReality, criteria.getAlternateReality());
		conflictQueryBuilder.setSort(criteria.getSort());
		conflictQueryBuilder.fetch(Conflict_.locations, doFetch);
		conflictQueryBuilder.fetch(Conflict_.firstSideBelligerents, doFetch);
		conflictQueryBuilder.fetch(Conflict_.firstSideCommanders, doFetch);
		conflictQueryBuilder.fetch(Conflict_.secondSideBelligerents, doFetch);
		conflictQueryBuilder.fetch(Conflict_.secondSideCommanders, doFetch);

		Page<Conflict> conflictPage = conflictQueryBuilder.findPage();
		clearProxies(conflictPage, !doFetch);
		return conflictPage;
	}

	@Override
	protected void clearProxies(Page<Conflict> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(conflict -> {
			conflict.setLocations(Sets.newHashSet());
			conflict.setFirstSideBelligerents(Sets.newHashSet());
			conflict.setFirstSideCommanders(Sets.newHashSet());
			conflict.setSecondSideBelligerents(Sets.newHashSet());
			conflict.setSecondSideCommanders(Sets.newHashSet());
		});
	}
}
