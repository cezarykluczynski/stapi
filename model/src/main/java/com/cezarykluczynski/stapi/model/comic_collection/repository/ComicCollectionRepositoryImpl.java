package com.cezarykluczynski.stapi.model.comic_collection.repository;

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection_;
import com.cezarykluczynski.stapi.model.comic_collection.query.ComicCollectionQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ComicCollectionRepositoryImpl implements ComicCollectionRepositoryCustom {

	private final ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory;

	public ComicCollectionRepositoryImpl(ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory) {
		this.comicCollectionQueryBuilderFactory = comicCollectionQueryBuilderFactory;
	}

	@Override
	public Page<ComicCollection> findMatching(ComicCollectionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<ComicCollection> comicCollectionQueryBuilder = comicCollectionQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		comicCollectionQueryBuilder.equal(ComicCollection_.uid, uid);
		comicCollectionQueryBuilder.like(ComicCollection_.title, criteria.getTitle());
		comicCollectionQueryBuilder.between(ComicCollection_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		comicCollectionQueryBuilder.between(ComicCollection_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		comicCollectionQueryBuilder.between(ComicCollection_.yearFrom, criteria.getYearFrom(), null);
		comicCollectionQueryBuilder.between(ComicCollection_.yearTo, null, criteria.getYearTo());
		comicCollectionQueryBuilder.between(ComicCollection_.stardateFrom, criteria.getStardateFrom(), null);
		comicCollectionQueryBuilder.between(ComicCollection_.stardateTo, null, criteria.getStardateTo());
		comicCollectionQueryBuilder.equal(ComicCollection_.photonovel, criteria.getPhotonovel());
		comicCollectionQueryBuilder.setSort(criteria.getSort());
		comicCollectionQueryBuilder.fetch(ComicCollection_.writers, doFetch);
		comicCollectionQueryBuilder.fetch(ComicCollection_.editors, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.artists, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.staff, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.comicSeries, doFetch);
		comicCollectionQueryBuilder.fetch(ComicCollection_.childComicSeries, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.publishers, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.comics, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.characters, doFetch);
		comicCollectionQueryBuilder.divideQueries();
		comicCollectionQueryBuilder.fetch(ComicCollection_.references, doFetch);

		return comicCollectionQueryBuilder.findPage();
	}

}
