package com.cezarykluczynski.stapi.model.api_key.repository;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

	List<ApiKey> findAllByAccountId(Long id);

}
