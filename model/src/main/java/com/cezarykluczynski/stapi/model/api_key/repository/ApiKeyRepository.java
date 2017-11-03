package com.cezarykluczynski.stapi.model.api_key.repository;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

}
