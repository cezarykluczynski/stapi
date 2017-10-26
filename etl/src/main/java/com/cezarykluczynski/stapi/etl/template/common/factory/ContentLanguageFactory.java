package com.cezarykluczynski.stapi.etl.template.common.factory;

import com.cezarykluczynski.stapi.etl.content_language.dto.ContentLanguageDTO;
import com.cezarykluczynski.stapi.etl.content_language.service.ContentLanguageDTOProvider;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.model.content_language.repository.ContentLanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ContentLanguageFactory {

	private final ContentLanguageDTOProvider contentLanguageDTOProvider;

	private final ContentLanguageRepository contentLanguageRepository;

	private final UidGenerator uidGenerator;

	public ContentLanguageFactory(ContentLanguageDTOProvider contentLanguageDTOProvider, ContentLanguageRepository contentLanguageRepository,
			UidGenerator uidGenerator) {
		this.contentLanguageDTOProvider = contentLanguageDTOProvider;
		this.contentLanguageRepository = contentLanguageRepository;
		this.uidGenerator = uidGenerator;
	}

	public synchronized Optional<ContentLanguage> createForName(String languageName) {
		Optional<ContentLanguageDTO> contentLanguageDTOOptional = contentLanguageDTOProvider.getByName(languageName);

		if (!contentLanguageDTOOptional.isPresent()) {
			log.warn("Could not create ContentLanguage entity for language named: \"{}\"", languageName);
			return Optional.empty();
		}

		ContentLanguageDTO contentLanguageDTO = contentLanguageDTOOptional.get();

		Optional<ContentLanguage> contentLanguageOptional = contentLanguageRepository.findByName(contentLanguageDTO.getName());
		if (contentLanguageOptional.isPresent()) {
			return contentLanguageOptional;
		}

		ContentLanguage contentLanguage = new ContentLanguage();
		contentLanguage.setName(contentLanguageDTO.getName());
		contentLanguage.setIso6391Code(contentLanguageDTO.getCode());
		contentLanguage.setUid(uidGenerator.generateForContentLanguage(contentLanguageDTO.getCode()));
		contentLanguageRepository.save(contentLanguage);
		return Optional.of(contentLanguage);
	}

}
