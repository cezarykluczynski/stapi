package com.cezarykluczynski.stapi.model.comics.repository;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.entity.Comics_;
import com.cezarykluczynski.stapi.model.comics.query.ComicsInitialQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComicsRepositoryImpl extends AbstractRepositoryImpl<Comics> implements ComicsRepositoryCustom {

	private final ComicsInitialQueryBuilderFactory comicsInitialQueryBuilderFactory;

	public ComicsRepositoryImpl(ComicsInitialQueryBuilderFactory comicsInitialQueryBuilderFactory) {
		this.comicsInitialQueryBuilderFactory = comicsInitialQueryBuilderFactory;
	}

	@Override
	public Page<Comics> findMatching(ComicsRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Comics> comicsQueryBuilder = createInitialComicsQueryBuilder(criteria, pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		Page<Comics> comicsPage;

		if (doFetch) {
			comicsQueryBuilder.fetch(Comics_.writers);
			comicsQueryBuilder.fetch(Comics_.artists);
			comicsQueryBuilder.fetch(Comics_.editors);
			comicsQueryBuilder.fetch(Comics_.staff);
			comicsPage = comicsQueryBuilder.findPage();

			List<Comics> comicsList = comicsPage.getContent();

			if (comicsList.size() == 0) {
				return comicsPage;
			}

			Comics comics = comicsList.get(0);

			QueryBuilder<Comics> comicsComicsSeriesPublishersComicCollectionsQueryBuilder = createInitialComicsQueryBuilder(criteria, pageable);

			comicsComicsSeriesPublishersComicCollectionsQueryBuilder.fetch(Comics_.comicSeries);
			comicsComicsSeriesPublishersComicCollectionsQueryBuilder.fetch(Comics_.publishers);
			comicsComicsSeriesPublishersComicCollectionsQueryBuilder.fetch(Comics_.comicCollections);

			List<Comics> comicSeriesPublishersComicsList = comicsComicsSeriesPublishersComicCollectionsQueryBuilder.findAll();

			if (comicSeriesPublishersComicsList.size() == 1) {
				Comics comicSeriesPublishersComicCollectionsComics = comicSeriesPublishersComicsList.get(0);
				comics.setComicSeries(comicSeriesPublishersComicCollectionsComics.getComicSeries());
				comics.setPublishers(comicSeriesPublishersComicCollectionsComics.getPublishers());
				comics.setComicCollections(comicSeriesPublishersComicCollectionsComics.getComicCollections());
			}

			QueryBuilder<Comics> comicsCharactersReferencesQueryBuilder = createInitialComicsQueryBuilder(criteria, pageable);
			comicsCharactersReferencesQueryBuilder.fetch(Comics_.characters);
			comicsCharactersReferencesQueryBuilder.fetch(Comics_.references);

			List<Comics> charactersReferencesComicsList = comicsCharactersReferencesQueryBuilder.findAll();

			if (charactersReferencesComicsList.size() == 1) {
				Comics charactersReferencesComics = charactersReferencesComicsList.get(0);
				comics.setCharacters(charactersReferencesComics.getCharacters());
				comics.setReferences(charactersReferencesComics.getReferences());
			}
		} else {
			comicsPage = comicsQueryBuilder.findPage();
		}

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

	private QueryBuilder<Comics> createInitialComicsQueryBuilder(ComicsRequestDTO criteria, Pageable pageable) {
		return comicsInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
