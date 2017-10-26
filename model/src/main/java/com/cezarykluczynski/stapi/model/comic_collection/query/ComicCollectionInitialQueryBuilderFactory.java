package com.cezarykluczynski.stapi.model.comic_collection.query;

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection_;
import com.cezarykluczynski.stapi.model.common.query.InitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionInitialQueryBuilderFactory implements InitialQueryBuilderFactory<ComicCollectionRequestDTO, ComicCollection> {

	private final ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory;

	public ComicCollectionInitialQueryBuilderFactory(ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory) {
		this.comicCollectionQueryBuilderFactory = comicCollectionQueryBuilderFactory;
	}

	@Override
	public QueryBuilder<ComicCollection> createInitialQueryBuilder(ComicCollectionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<ComicCollection> comicsQueryBuilder = comicCollectionQueryBuilderFactory.createQueryBuilder(pageable);

		comicsQueryBuilder.equal(ComicCollection_.uid, criteria.getUid());
		comicsQueryBuilder.like(ComicCollection_.title, criteria.getTitle());
		comicsQueryBuilder.between(ComicCollection_.publishedYear, criteria.getPublishedYearFrom(), criteria.getPublishedYearTo());
		comicsQueryBuilder.between(ComicCollection_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		comicsQueryBuilder.between(ComicCollection_.yearFrom, criteria.getYearFrom(), null);
		comicsQueryBuilder.between(ComicCollection_.yearTo, null, criteria.getYearTo());
		comicsQueryBuilder.between(ComicCollection_.stardateFrom, criteria.getStardateFrom(), null);
		comicsQueryBuilder.between(ComicCollection_.stardateTo, null, criteria.getStardateTo());
		comicsQueryBuilder.equal(ComicCollection_.photonovel, criteria.getPhotonovel());
		comicsQueryBuilder.setSort(criteria.getSort());

		return comicsQueryBuilder;
	}
}
