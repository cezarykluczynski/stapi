package com.cezarykluczynski.stapi.model.api_key.repository;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

	List<ApiKey> findAllByAccountId(Long id);

	Page<ApiKey> findAllByAccountId(Long accountId, Pageable pageable);

	Page<ApiKey> findAllByAccountIdAndId(Long accountId, Long apiKeyId, Pageable pageable);

	Optional<ApiKey> findByApiKey(String apiKey);

	long countByAccountId(Long id);

	void deleteByAccountId(Long accountId);
}
