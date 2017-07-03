package com.cezarykluczynski.stapi.model.content_language.repository;

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentLanguageRepository extends JpaRepository<ContentLanguage, Long> {

	Optional<ContentLanguage> findByName(String name);

}
