package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.entity.Performer_;
import com.cezarykluczynski.stapi.model.performer.query.PerformerQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PerformerRepositoryImpl implements PerformerRepositoryCustom {

	private final PerformerQueryBuilderFactory performerQueryBuilderFactory;

	public PerformerRepositoryImpl(PerformerQueryBuilderFactory performerQueryBuilderFactory) {
		this.performerQueryBuilderFactory = performerQueryBuilderFactory;
	}

	@Override
	public Page<Performer> findMatching(PerformerRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Performer> performerQueryBuilder = performerQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		performerQueryBuilder.equal(Performer_.uid, uid);
		performerQueryBuilder.like(Performer_.name, criteria.getName());
		performerQueryBuilder.like(Performer_.birthName, criteria.getBirthName());
		performerQueryBuilder.like(Performer_.placeOfBirth, criteria.getPlaceOfBirth());
		performerQueryBuilder.like(Performer_.placeOfDeath, criteria.getPlaceOfDeath());
		performerQueryBuilder.between(Performer_.dateOfBirth, criteria.getDateOfBirthFrom(), criteria.getDateOfBirthTo());
		performerQueryBuilder.between(Performer_.dateOfDeath, criteria.getDateOfDeathFrom(), criteria.getDateOfDeathTo());
		performerQueryBuilder.equal(Performer_.gender, criteria.getGender());
		performerQueryBuilder.equal(Performer_.animalPerformer, criteria.getAnimalPerformer());
		performerQueryBuilder.equal(Performer_.audiobookPerformer, criteria.getAudiobookPerformer());
		performerQueryBuilder.equal(Performer_.cutPerformer, criteria.getCutPerformer());
		performerQueryBuilder.equal(Performer_.disPerformer, criteria.getDisPerformer());
		performerQueryBuilder.equal(Performer_.ds9Performer, criteria.getDs9Performer());
		performerQueryBuilder.equal(Performer_.entPerformer, criteria.getEntPerformer());
		performerQueryBuilder.equal(Performer_.filmPerformer, criteria.getFilmPerformer());
		performerQueryBuilder.equal(Performer_.ldPerformer, criteria.getLdPerformer());
		performerQueryBuilder.equal(Performer_.picPerformer, criteria.getPicPerformer());
		performerQueryBuilder.equal(Performer_.proPerformer, criteria.getProPerformer());
		performerQueryBuilder.equal(Performer_.puppeteer, criteria.getPuppeteer());
		performerQueryBuilder.equal(Performer_.snwPerformer, criteria.getSnwPerformer());
		performerQueryBuilder.equal(Performer_.standInPerformer, criteria.getStandInPerformer());
		performerQueryBuilder.equal(Performer_.stPerformer, criteria.getStPerformer());
		performerQueryBuilder.equal(Performer_.stuntPerformer, criteria.getStuntPerformer());
		performerQueryBuilder.equal(Performer_.tasPerformer, criteria.getTasPerformer());
		performerQueryBuilder.equal(Performer_.tngPerformer, criteria.getTngPerformer());
		performerQueryBuilder.equal(Performer_.tosPerformer, criteria.getTosPerformer());
		performerQueryBuilder.equal(Performer_.videoGamePerformer, criteria.getVideoGamePerformer());
		performerQueryBuilder.equal(Performer_.voicePerformer, criteria.getVoicePerformer());
		performerQueryBuilder.equal(Performer_.voyPerformer, criteria.getVoyPerformer());
		performerQueryBuilder.setSort(criteria.getSort());
		performerQueryBuilder.fetch(Performer_.episodesPerformances, doFetch);
		performerQueryBuilder.divideQueries();
		performerQueryBuilder.fetch(Performer_.episodesStandInPerformances, doFetch);
		performerQueryBuilder.fetch(Performer_.episodesStuntPerformances, doFetch);
		performerQueryBuilder.divideQueries();
		performerQueryBuilder.fetch(Performer_.moviesPerformances, doFetch);
		performerQueryBuilder.fetch(Performer_.moviesStandInPerformances, doFetch);
		performerQueryBuilder.fetch(Performer_.moviesStuntPerformances, doFetch);
		performerQueryBuilder.divideQueries();
		performerQueryBuilder.fetch(Performer_.characters, doFetch);
		performerQueryBuilder.divideQueries();
		performerQueryBuilder.fetch(Performer_.externalLinks, doFetch);

		return performerQueryBuilder.findPage();
	}

}
