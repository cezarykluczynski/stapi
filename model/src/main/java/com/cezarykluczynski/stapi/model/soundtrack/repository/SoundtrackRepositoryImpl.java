package com.cezarykluczynski.stapi.model.soundtrack.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack_;
import com.cezarykluczynski.stapi.model.soundtrack.query.SoundtrackQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SoundtrackRepositoryImpl extends AbstractRepositoryImpl<Soundtrack> implements SoundtrackRepositoryCustom {

	private final SoundtrackQueryBuilderFactory soundtrackQueryBuilderFactory;

	public SoundtrackRepositoryImpl(SoundtrackQueryBuilderFactory soundtrackQueryBuilderFactory) {
		this.soundtrackQueryBuilderFactory = soundtrackQueryBuilderFactory;
	}

	@Override
	public Page<Soundtrack> findMatching(SoundtrackRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Soundtrack> soundtrackQueryBuilder = soundtrackQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		soundtrackQueryBuilder.equal(Soundtrack_.uid, uid);
		soundtrackQueryBuilder.like(Soundtrack_.title, criteria.getTitle());
		soundtrackQueryBuilder.between(Soundtrack_.releaseDate, criteria.getReleaseDateFrom(), criteria.getReleaseDateTo());
		soundtrackQueryBuilder.between(Soundtrack_.length, criteria.getLengthFrom(), criteria.getLengthTo());
		soundtrackQueryBuilder.setSort(criteria.getSort());
		soundtrackQueryBuilder.fetch(Soundtrack_.labels, doFetch);
		soundtrackQueryBuilder.fetch(Soundtrack_.composers, doFetch);
		soundtrackQueryBuilder.fetch(Soundtrack_.contributors, doFetch);
		soundtrackQueryBuilder.fetch(Soundtrack_.orchestrators, doFetch);
		soundtrackQueryBuilder.fetch(Soundtrack_.references, doFetch);

		Page<Soundtrack> soundtrackPage = soundtrackQueryBuilder.findPage();
		clearProxies(soundtrackPage, !doFetch);
		return soundtrackPage;
	}

	@Override
	protected void clearProxies(Page<Soundtrack> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(soundtrack -> {
			soundtrack.setLabels(Sets.newHashSet());
			soundtrack.setComposers(Sets.newHashSet());
			soundtrack.setContributors(Sets.newHashSet());
			soundtrack.setOrchestrators(Sets.newHashSet());
			soundtrack.setReferences(Sets.newHashSet());
		});
	}

}
