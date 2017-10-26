package com.cezarykluczynski.stapi.etl.template.common.factory;

import com.cezarykluczynski.stapi.etl.genre.service.GenreNameNormalizationService;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import com.cezarykluczynski.stapi.model.genre.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreFactory {

	private final GenreNameNormalizationService genreNameNormalizationService;

	private final GenreRepository genreRepository;

	private final UidGenerator uidGenerator;

	public GenreFactory(GenreNameNormalizationService genreNameNormalizationService, GenreRepository genreRepository,
			UidGenerator uidGenerator) {
		this.genreNameNormalizationService = genreNameNormalizationService;
		this.genreRepository = genreRepository;
		this.uidGenerator = uidGenerator;
	}

	public synchronized Genre createForName(String genreName) {
		String normalizedGenreName = genreNameNormalizationService.normalize(genreName);
		Optional<Genre> genreOptional = genreRepository.findByName(normalizedGenreName);
		if (genreOptional.isPresent()) {
			return genreOptional.get();
		} else {
			Genre genre = new Genre();
			genre.setName(normalizedGenreName);
			genre.setUid(uidGenerator.generateForGenre(normalizedGenreName));
			genreRepository.save(genre);
			return genre;
		}
	}

}
