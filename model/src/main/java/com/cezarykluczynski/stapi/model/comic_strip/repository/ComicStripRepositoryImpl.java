package com.cezarykluczynski.stapi.model.comic_strip.repository;

import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip_;
import com.cezarykluczynski.stapi.model.comic_strip.query.ComicStripQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ComicStripRepositoryImpl implements ComicStripRepositoryCustom {

	private final ComicStripQueryBuilderFactory comicStripQueryBuilderFactory;

	public ComicStripRepositoryImpl(ComicStripQueryBuilderFactory comicStripQueryBuilderFactory) {
		this.comicStripQueryBuilderFactory = comicStripQueryBuilderFactory;
	}

	@Override
	public Page<ComicStrip> findMatching(ComicStripRequestDTO criteria, Pageable pageable) {
		QueryBuilder<ComicStrip> comicStripQueryBuilder = comicStripQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		comicStripQueryBuilder.equal(ComicStrip_.uid, uid);
		comicStripQueryBuilder.like(ComicStrip_.title, criteria.getTitle());
		comicStripQueryBuilder.between(ComicStrip_.publishedYearFrom, criteria.getPublishedYearFrom(), null);
		comicStripQueryBuilder.between(ComicStrip_.publishedYearTo, null, criteria.getPublishedYearTo());
		comicStripQueryBuilder.between(ComicStrip_.numberOfPages, criteria.getNumberOfPagesFrom(), criteria.getNumberOfPagesTo());
		comicStripQueryBuilder.between(ComicStrip_.yearFrom, criteria.getYearFrom(), null);
		comicStripQueryBuilder.between(ComicStrip_.yearTo, null, criteria.getYearTo());
		comicStripQueryBuilder.setSort(criteria.getSort());
		comicStripQueryBuilder.fetch(ComicStrip_.comicSeries, doFetch);
		comicStripQueryBuilder.fetch(ComicStrip_.writers, doFetch);
		comicStripQueryBuilder.fetch(ComicStrip_.artists, doFetch);
		comicStripQueryBuilder.fetch(ComicStrip_.characters, doFetch);

		return comicStripQueryBuilder.findPage();
	}

}
