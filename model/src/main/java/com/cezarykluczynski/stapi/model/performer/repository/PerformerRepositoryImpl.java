package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.entity.Performer_;
import com.cezarykluczynski.stapi.model.performer.query.PerformerQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository
public class PerformerRepositoryImpl extends AbstractRepositoryImpl<Performer> implements PerformerRepositoryCustom {

	private PerformerQueryBuilderFactory performerQueryBuilderFactory;

	@Inject
	public PerformerRepositoryImpl(PerformerQueryBuilderFactory performerQueryBuilderFactory) {
		this.performerQueryBuilderFactory = performerQueryBuilderFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Performer> findMatching(PerformerRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Performer> performerQueryBuilder = performerQueryBuilderFactory.createQueryBuilder(pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

		performerQueryBuilder.equal(Performer_.guid, guid);
		performerQueryBuilder.like(Performer_.name, criteria.getName());
		performerQueryBuilder.like(Performer_.birthName, criteria.getBirthName());
		performerQueryBuilder.like(Performer_.placeOfBirth, criteria.getPlaceOfBirth());
		performerQueryBuilder.like(Performer_.placeOfDeath, criteria.getPlaceOfDeath());
		performerQueryBuilder.between(Performer_.dateOfBirth, criteria.getDateOfBirthFrom(),
				criteria.getDateOfBirthTo());
		performerQueryBuilder.between(Performer_.dateOfDeath, criteria.getDateOfDeathFrom(),
				criteria.getDateOfDeathTo());
		performerQueryBuilder.equal(Performer_.gender, criteria.getGender());
		performerQueryBuilder.equal(Performer_.animalPerformer, criteria.getAnimalPerformer());
		performerQueryBuilder.equal(Performer_.disPerformer, criteria.getDisPerformer());
		performerQueryBuilder.equal(Performer_.ds9Performer, criteria.getDs9Performer());
		performerQueryBuilder.equal(Performer_.entPerformer, criteria.getEntPerformer());
		performerQueryBuilder.equal(Performer_.filmPerformer, criteria.getFilmPerformer());
		performerQueryBuilder.equal(Performer_.standInPerformer, criteria.getStandInPerformer());
		performerQueryBuilder.equal(Performer_.stuntPerformer, criteria.getStuntPerformer());
		performerQueryBuilder.equal(Performer_.tasPerformer, criteria.getTasPerformer());
		performerQueryBuilder.equal(Performer_.tngPerformer, criteria.getTngPerformer());
		performerQueryBuilder.equal(Performer_.tosPerformer, criteria.getTosPerformer());
		performerQueryBuilder.equal(Performer_.videoGamePerformer, criteria.getVideoGamePerformer());
		performerQueryBuilder.equal(Performer_.voicePerformer, criteria.getVoicePerformer());
		performerQueryBuilder.equal(Performer_.voyPerformer, criteria.getVoyPerformer());
		performerQueryBuilder.setSort(criteria.getSort());
		performerQueryBuilder.fetch(Performer_.performances, doFetch);
		performerQueryBuilder.fetch(Performer_.standInPerformances, doFetch);
		performerQueryBuilder.fetch(Performer_.stuntPerformances, doFetch);
		performerQueryBuilder.fetch(Performer_.characters, doFetch);

		Page<Performer> performerPage = performerQueryBuilder.findPage();
		clearProxies(performerPage, !doFetch);
		return performerPage;
	}

	@Override
	protected void clearProxies(Page<Performer> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(performer -> {
			performer.setPerformances(Sets.newHashSet());
			performer.setStuntPerformances(Sets.newHashSet());
			performer.setStandInPerformances(Sets.newHashSet());
			performer.setCharacters(Sets.newHashSet());
		});
	}

}
