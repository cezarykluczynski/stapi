package com.cezarykluczynski.stapi.auth.api_key.operation.read

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ApiKeysReaderTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L
	private static final int PAGE_NUMBER = 2
	private static final int ADMIN_PAGE_SIZE = 20

	private ApiKeyRepository apiKeyRepositoryMock

	private ApiKeyProperties apiKeyPropertiesMock

	private ApiKeysReader apiKeysReader

	void setup() {
		apiKeyRepositoryMock = Mock()
		apiKeyPropertiesMock = new ApiKeyProperties(adminPageSize: ADMIN_PAGE_SIZE)
		apiKeysReader = new ApiKeysReader(apiKeyRepositoryMock, apiKeyPropertiesMock)
	}

	void "does not allow null page number"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(pageNumber: null)

		when:
		apiKeysReader.execute(apiKeysReadCriteria)

		then:
		0 * _
		thrown(NullPointerException)
	}

	void "does not allow request with API key ID, but without account ID"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(
				apiKeyId: API_KEY_ID,
				pageNumber: 0)

		when:
		apiKeysReader.execute(apiKeysReadCriteria)

		then:
		0 * _
		thrown(IllegalStateException)
	}

	void "find page with no account ID nor API key ID"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(pageNumber: PAGE_NUMBER)
		Page<ApiKey> page = Mock()

		when:
		Page<ApiKey> pageOutput = apiKeysReader.execute(apiKeysReadCriteria)

		then:
		1 * apiKeyRepositoryMock.findAll(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == PAGE_NUMBER
			assert pageable.pageSize == ADMIN_PAGE_SIZE
			page
		}
		0 * _
		pageOutput == page
	}

	void "find page with account ID and without API key ID"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(
				accountId: ACCOUNT_ID,
				pageNumber: PAGE_NUMBER)
		Page<ApiKey> page = Mock()

		when:
		Page<ApiKey> pageOutput = apiKeysReader.execute(apiKeysReadCriteria)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID, _ as Pageable) >> { Long accountId, Pageable pageable ->
			assert pageable.pageNumber == PAGE_NUMBER
			assert pageable.pageSize == ADMIN_PAGE_SIZE
			page
		}
		0 * _
		pageOutput == page
	}

	void "find page with account ID and API key ID"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(
				accountId: ACCOUNT_ID,
				apiKeyId: API_KEY_ID,
				pageNumber: PAGE_NUMBER)
		Page<ApiKey> page = Mock()

		when:
		Page<ApiKey> pageOutput = apiKeysReader.execute(apiKeysReadCriteria)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountIdAndId(ACCOUNT_ID, API_KEY_ID, _ as Pageable) >> {
				Long accountId, Long apiKeyId, Pageable pageable ->
			assert pageable.pageNumber == PAGE_NUMBER
			assert pageable.pageSize == ADMIN_PAGE_SIZE
			page
		}
		0 * _
		pageOutput == page
	}

}
