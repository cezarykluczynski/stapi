package com.cezarykluczynski.stapi.model.spacecraft_class.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft_;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass_;
import com.cezarykluczynski.stapi.model.spacecraft_class.query.SpacecraftClassQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.species.entity.Species_;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SpacecraftClassRepositoryImpl extends AbstractRepositoryImpl<SpacecraftClass> implements SpacecraftClassRepositoryCustom {

	private final SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactory;

	public SpacecraftClassRepositoryImpl(SpacecraftClassQueryBuilderFactory spacecraftClassQueryBuilderFactory) {
		this.spacecraftClassQueryBuilderFactory = spacecraftClassQueryBuilderFactory;
	}

	@Override
	public Page<SpacecraftClass> findMatching(SpacecraftClassRequestDTO criteria, Pageable pageable) {
		QueryBuilder<SpacecraftClass> spacecraftClassQueryBuilder = spacecraftClassQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		spacecraftClassQueryBuilder.equal(SpacecraftClass_.uid, uid);
		spacecraftClassQueryBuilder.like(SpacecraftClass_.name, criteria.getName());
		spacecraftClassQueryBuilder.equal(SpacecraftClass_.warpCapable, criteria.getWarpCapable());
		spacecraftClassQueryBuilder.equal(SpacecraftClass_.alternateReality, criteria.getAlternateReality());
		spacecraftClassQueryBuilder.setSort(criteria.getSort());
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species, Species_.homeworld, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.species, Species_.quadrant, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.owner);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.operator);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.affiliation);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecraftTypes, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, Spacecraft_.owner, doFetch);
		spacecraftClassQueryBuilder.fetch(SpacecraftClass_.spacecrafts, Spacecraft_.operator, doFetch);

		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassQueryBuilder.findPage();
		clearProxies(spacecraftClassPage, !doFetch);
		return spacecraftClassPage;
	}

	@Override
	protected void clearProxies(Page<SpacecraftClass> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(spacecraftClass -> {
			spacecraftClass.setSpacecraftTypes(Sets.newHashSet());
			spacecraftClass.setSpacecrafts(Sets.newHashSet());
		});
	}

}
