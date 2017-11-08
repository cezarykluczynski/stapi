package com.cezarykluczynski.stapi.auth.api_key.mapper

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class ApiKeyMapperTest extends Specification {

	private static final Long ID = 5L
	private static final String API_KEY = 'API_KEY'
	private static final Long ACCOUNT_ID = 10L
	private static final Integer LIMIT = 1000
	private static final Boolean ACTIVE = RandomUtil.nextBoolean()
	private static final Boolean BLOCKED = RandomUtil.nextBoolean()
	private static final String DESCRIPTION = 'DESCRIPTION'

	private ApiKeyMapper apiKeyMapper

	void setup() {
		apiKeyMapper = new ApiKeyMapper()
	}

	void "throws exception when null is passed"() {
		when:
		apiKeyMapper.map(null)

		then:
		thrown(StapiRuntimeException)
	}

	void "maps ApiKey with account ID to ApiKeyDTO"() {
		when:
		ApiKeyDTO apiKeyDTO = apiKeyMapper.map(new ApiKey(
				id: ID,
				apiKey: API_KEY,
				accountId: ACCOUNT_ID,
				limit: LIMIT,
				active: ACTIVE,
				blocked: BLOCKED,
				description: DESCRIPTION))

		then:
		apiKeyDTO.id == ID
		apiKeyDTO.apiKey == API_KEY
		apiKeyDTO.accountId == ACCOUNT_ID
		apiKeyDTO.limit == LIMIT
		apiKeyDTO.active == ACTIVE
		apiKeyDTO.blocked == BLOCKED
		apiKeyDTO.description == DESCRIPTION
	}

	void "maps ApiKey with Account to ApiKeyDTO"() {
		when:
		ApiKeyDTO apiKeyDTO = apiKeyMapper.map(new ApiKey(
				id: ID,
				apiKey: API_KEY,
				account: new Account(id: ACCOUNT_ID),
				limit: LIMIT,
				active: ACTIVE,
				blocked: BLOCKED,
				description: DESCRIPTION))

		then:
		apiKeyDTO.id == ID
		apiKeyDTO.apiKey == API_KEY
		apiKeyDTO.accountId == ACCOUNT_ID
		apiKeyDTO.limit == LIMIT
		apiKeyDTO.active == ACTIVE
		apiKeyDTO.blocked == BLOCKED
		apiKeyDTO.description == DESCRIPTION
	}

}
