package com.cezarykluczynski.stapi.auth.api_key.operation.removal

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class ApiKeyRemovalExceptionMapperTest extends Specification {

	private ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactoryMock

	private ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapper

	void setup() {
		apiKeyRemovalResponseDTOFactoryMock = Mock()
		apiKeyRemovalExceptionMapper = new ApiKeyRemovalExceptionMapper(apiKeyRemovalResponseDTOFactoryMock)
	}

	void "maps KeyDoesNotExistException to ApiKeyRemovalResponseDTO"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalExceptionMapper.map(new KeyDoesNotExistException())

		then:
		1 * apiKeyRemovalResponseDTOFactoryMock.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.KEY_DOES_NOT_EXIST) >>
				apiKeyRemovalResponseDTO
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

	void "maps KeyNotOwnedByAccountException to ApiKeyRemovalResponseDTO"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalExceptionMapper.map(new KeyNotOwnedByAccountException())

		then:
		1 * apiKeyRemovalResponseDTOFactoryMock.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.KEY_NOT_OWNED_BY_ACCOUNT) >>
				apiKeyRemovalResponseDTO
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

	void "maps BlockedKeyException to ApiKeyRemovalResponseDTO"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalExceptionMapper.map(new BlockedKeyException())

		then:
		1 * apiKeyRemovalResponseDTOFactoryMock.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.BLOCKED_KEY) >> apiKeyRemovalResponseDTO
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

	void "throws exception when not mapped exception is passed"() {
		when:
		apiKeyRemovalExceptionMapper.map(new NotMappedException(''))

		then:
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == 'Exception NotMappedException not mapped to ApiKeyRemovalResponseDTO.FailReason'
	}

	class NotMappedException extends ApiKeyException {

		NotMappedException(String message) {
			super(message)
		}

	}

}
