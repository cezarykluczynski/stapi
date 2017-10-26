package com.cezarykluczynski.stapi.model.comic_collection.repository;

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection_;
import com.cezarykluczynski.stapi.model.comic_collection.query.ComicCollectionInitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComicCollectionRepositoryImpl extends AbstractRepositoryImpl<ComicCollection> implements ComicCollectionRepositoryCustom {

	private final ComicCollectionInitialQueryBuilderFactory comicCollectionInitialQueryBuilderFactory;

	public ComicCollectionRepositoryImpl(ComicCollectionInitialQueryBuilderFactory comicCollectionInitialQueryBuilderFactory) {
		this.comicCollectionInitialQueryBuilderFactory = comicCollectionInitialQueryBuilderFactory;
	}

	@Override
	public Page<ComicCollection> findMatching(ComicCollectionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<ComicCollection> comicCollectionQueryBuilder = createInitialComicCollectionQueryBuilder(criteria, pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		Page<ComicCollection> comicCollectionCollectionPage;

		if (doFetch) {
			comicCollectionQueryBuilder.fetch(ComicCollection_.writers);
			comicCollectionQueryBuilder.fetch(ComicCollection_.artists);
			comicCollectionQueryBuilder.fetch(ComicCollection_.editors);
			comicCollectionQueryBuilder.fetch(ComicCollection_.staff);
			comicCollectionCollectionPage = comicCollectionQueryBuilder.findPage();

			List<ComicCollection> comicCollectionList = comicCollectionCollectionPage.getContent();

			if (comicCollectionList.size() == 0) {
				return comicCollectionCollectionPage;
			}

			ComicCollection comicCollection = comicCollectionList.get(0);

			QueryBuilder<ComicCollection> comicCollectionComicsSeriesPublishersComicsQueryBuilder
					= createInitialComicCollectionQueryBuilder(criteria, pageable);

			comicCollectionComicsSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.comicSeries);
			comicCollectionComicsSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.publishers);
			comicCollectionComicsSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.comics);

			List<ComicCollection> comicCollectionComicSeriesPublishersComicsList = comicCollectionComicsSeriesPublishersComicsQueryBuilder.findAll();

			if (comicCollectionComicSeriesPublishersComicsList.size() == 1) {
				ComicCollection comicSeriesPublishersComicCollection = comicCollectionComicSeriesPublishersComicsList.get(0);
				comicCollection.setComicSeries(comicSeriesPublishersComicCollection.getComicSeries());
				comicCollection.setPublishers(comicSeriesPublishersComicCollection.getPublishers());
				comicCollection.setComics(comicSeriesPublishersComicCollection.getComics());
			}

			QueryBuilder<ComicCollection> comicsCharactersReferencesQueryBuilder = createInitialComicCollectionQueryBuilder(criteria, pageable);
			comicsCharactersReferencesQueryBuilder.fetch(ComicCollection_.characters);
			comicsCharactersReferencesQueryBuilder.fetch(ComicCollection_.references);

			List<ComicCollection> charactersReferencesComicCollectionList = comicsCharactersReferencesQueryBuilder.findAll();

			if (charactersReferencesComicCollectionList.size() == 1) {
				ComicCollection charactersReferencesComicCollection = charactersReferencesComicCollectionList.get(0);
				comicCollection.setCharacters(charactersReferencesComicCollection.getCharacters());
				comicCollection.setReferences(charactersReferencesComicCollection.getReferences());
			}
		} else {
			comicCollectionCollectionPage = comicCollectionQueryBuilder.findPage();
		}

		clearProxies(comicCollectionCollectionPage, !doFetch);
		return comicCollectionCollectionPage;
	}

	@Override
	protected void clearProxies(Page<ComicCollection> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(comicCollection -> {
			comicCollection.setComicSeries(Sets.newHashSet());
			comicCollection.setWriters(Sets.newHashSet());
			comicCollection.setArtists(Sets.newHashSet());
			comicCollection.setEditors(Sets.newHashSet());
			comicCollection.setStaff(Sets.newHashSet());
			comicCollection.setPublishers(Sets.newHashSet());
			comicCollection.setCharacters(Sets.newHashSet());
			comicCollection.setReferences(Sets.newHashSet());
			comicCollection.setComics(Sets.newHashSet());
		});
	}

	private QueryBuilder<ComicCollection> createInitialComicCollectionQueryBuilder(ComicCollectionRequestDTO criteria, Pageable pageable) {
		return comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
