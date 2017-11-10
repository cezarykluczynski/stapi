package com.cezarykluczynski.stapi.model.api_key.repository;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

	List<ApiKey> findAllByAccountId(Long id);

	Page<ApiKey> findAllByAccountId(Long accountId, Pageable pageable);

	Page<ApiKey> findAllByAccountIdAndId(Long accountId, Long apiKeyId, Pageable pageable);

	long countByAccountId(Long id);

}
