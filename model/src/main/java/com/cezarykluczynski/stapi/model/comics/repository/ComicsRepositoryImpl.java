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

import javax.inject.Inject;
import java.util.List;

@Repository
public class ComicsRepositoryImpl extends AbstractRepositoryImpl<Comics> implements ComicsRepositoryCustom {

	private ComicsInitialQueryBuilderFactory comicsInitialQueryBuilderFactory;

	@Inject
	public ComicsRepositoryImpl(ComicsInitialQueryBuilderFactory comicsInitialQueryBuilderFactory) {
		this.comicsInitialQueryBuilderFactory = comicsInitialQueryBuilderFactory;
	}

	@Override
	public Page<Comics> findMatching(ComicsRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Comics> comicsQueryBuilder = createInitialComicsQueryBuilder(criteria, pageable);
		String guid = criteria.getGuid();
		boolean doFetch = guid != null;

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

			QueryBuilder<Comics> comicsComicsSeriesPublishersQueryBuilder = createInitialComicsQueryBuilder(criteria, pageable);

			comicsComicsSeriesPublishersQueryBuilder.fetch(Comics_.comicSeries);
			comicsComicsSeriesPublishersQueryBuilder.fetch(Comics_.publishers);

			List<Comics> comicSeriesPublishersComicsList = comicsComicsSeriesPublishersQueryBuilder.findAll();

			if (comicSeriesPublishersComicsList.size() == 1) {
				Comics comicSeriesPublishersComics = comicSeriesPublishersComicsList.get(0);
				comics.setComicSeries(comicSeriesPublishersComics.getComicSeries());
				comics.setPublishers(comicSeriesPublishersComics.getPublishers());
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

		page.getContent().forEach(episode -> {
			episode.setWriters(Sets.newHashSet());
			episode.setArtists(Sets.newHashSet());
			episode.setEditors(Sets.newHashSet());
			episode.setStaff(Sets.newHashSet());
			episode.setComicSeries(Sets.newHashSet());
			episode.setPublishers(Sets.newHashSet());
			episode.setCharacters(Sets.newHashSet());
			episode.setReferences(Sets.newHashSet());
		});
	}

	private QueryBuilder<Comics> createInitialComicsQueryBuilder(ComicsRequestDTO criteria, Pageable pageable) {
		return comicsInitialQueryBuilderFactory.createInitialQueryBuilder(criteria, pageable);
	}

}
