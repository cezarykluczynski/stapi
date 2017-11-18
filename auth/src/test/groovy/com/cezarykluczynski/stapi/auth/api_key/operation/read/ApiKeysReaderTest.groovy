package com.cezarykluczynski.stapi.auth.api_key.operation.read

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey_
import com.cezarykluczynski.stapi.model.api_key.query.ApiKeyQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ApiKeysReaderTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L
	private static final int PAGE_NUMBER = 2
	private static final int ADMIN_PAGE_SIZE = 20

	private ApiKeyQueryBuilderFactory apiKeyQueryBuilderFactoryMock

	private ApiKeyProperties apiKeyPropertiesMock

	private ApiKeysReader apiKeysReader

	void setup() {
		apiKeyQueryBuilderFactoryMock = Mock()
		apiKeyPropertiesMock = new ApiKeyProperties(adminPageSize: ADMIN_PAGE_SIZE)
		apiKeysReader = new ApiKeysReader(apiKeyQueryBuilderFactoryMock, apiKeyPropertiesMock)
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
		QueryBuilder<ApiKey> apiKeyQueryBuilder = Mock()
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(pageNumber: PAGE_NUMBER)
		Page<ApiKey> page = Mock()

		when:
		Page<ApiKey> pageOutput = apiKeysReader.execute(apiKeysReadCriteria)

		then:
		1 * apiKeyQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == PAGE_NUMBER
			assert pageable.pageSize == ADMIN_PAGE_SIZE
			apiKeyQueryBuilder
		}
		2 * apiKeyQueryBuilder.equal(_, null)
		1 * apiKeyQueryBuilder.fetch(ApiKey_.throttle)
		1 * apiKeyQueryBuilder.findPage() >> page
		0 * _
		pageOutput == page
	}

	void "find page with account ID and API key ID"() {
		given:
		QueryBuilder<ApiKey> apiKeyQueryBuilder = Mock()
		ApiKeysReadCriteria apiKeysReadCriteria = new ApiKeysReadCriteria(
				accountId: ACCOUNT_ID,
				apiKeyId: API_KEY_ID,
				pageNumber: PAGE_NUMBER)
		Page<ApiKey> page = Mock()

		when:
		Page<ApiKey> pageOutput = apiKeysReader.execute(apiKeysReadCriteria)

		then:
		1 * apiKeyQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == PAGE_NUMBER
			assert pageable.pageSize == ADMIN_PAGE_SIZE
			apiKeyQueryBuilder
		}
		1 * apiKeyQueryBuilder.equal(ApiKey_.accountId, ACCOUNT_ID)
		1 * apiKeyQueryBuilder.equal(ApiKey_.id, API_KEY_ID)
		1 * apiKeyQueryBuilder.fetch(ApiKey_.throttle)
		1 * apiKeyQueryBuilder.findPage() >> page
		0 * _
		pageOutput == page
	}

}
