package com.cezarykluczynski.stapi.model.comics.repository;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.entity.Comics_;
import com.cezarykluczynski.stapi.model.comics.query.ComicsQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ComicsRepositoryImpl extends AbstractRepositoryImpl<Comics> implements ComicsRepositoryCustom {

	private final ComicsQueryBuilderFactory comicsQueryBuilderFactory;

	public ComicsRepositoryImpl(ComicsQueryBuilderFactory comicsQueryBuilderFactory) {
		this.comicsQueryBuilderFactory = comicsQueryBuilderFactory;
	}

	@Override
	public Page<Comics> findMatching(ComicsRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Comics> comicsQueryBuilder = comicsQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		comicsQueryBuilder.equal(Comics_.uid, uid);
		comicsQueryBuilder.like(Comics_.title, criteria.getTitle());
		comicsQueryBuilder.between(Comics_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		comicsQueryBuilder.between(Comics_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		comicsQueryBuilder.between(Comics_.yearFrom, criteria.getYearFrom(), null);
		comicsQueryBuilder.between(Comics_.yearTo, null, criteria.getYearTo());
		comicsQueryBuilder.between(Comics_.stardateFrom, criteria.getStardateFrom(), null);
		comicsQueryBuilder.between(Comics_.stardateTo, null, criteria.getStardateTo());
		comicsQueryBuilder.equal(Comics_.photonovel, criteria.getPhotonovel());
		comicsQueryBuilder.equal(Comics_.adaptation, criteria.getAdaptation());
		comicsQueryBuilder.setSort(criteria.getSort());
		comicsQueryBuilder.fetch(Comics_.writers, doFetch);
		comicsQueryBuilder.fetch(Comics_.artists, doFetch);
		comicsQueryBuilder.fetch(Comics_.editors, doFetch);
		comicsQueryBuilder.divideQueries();
		comicsQueryBuilder.fetch(Comics_.staff, doFetch);
		comicsQueryBuilder.divideQueries();
		comicsQueryBuilder.fetch(Comics_.comicSeries, doFetch);
		comicsQueryBuilder.fetch(Comics_.publishers, doFetch);
		comicsQueryBuilder.fetch(Comics_.comicCollections, doFetch);
		comicsQueryBuilder.divideQueries();
		comicsQueryBuilder.fetch(Comics_.characters, doFetch);
		comicsQueryBuilder.divideQueries();
		comicsQueryBuilder.fetch(Comics_.references, doFetch);

		Page<Comics> comicsPage = comicsQueryBuilder.findPage();
		clearProxies(comicsPage, !doFetch);
		return comicsPage;
	}

	@Override
	protected void clearProxies(Page<Comics> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(comics -> {
			comics.setWriters(Sets.newHashSet());
			comics.setArtists(Sets.newHashSet());
			comics.setEditors(Sets.newHashSet());
			comics.setStaff(Sets.newHashSet());
			comics.setComicSeries(Sets.newHashSet());
			comics.setPublishers(Sets.newHashSet());
			comics.setCharacters(Sets.newHashSet());
			comics.setReferences(Sets.newHashSet());
			comics.setComicCollections(Sets.newHashSet());
		});
	}

}
