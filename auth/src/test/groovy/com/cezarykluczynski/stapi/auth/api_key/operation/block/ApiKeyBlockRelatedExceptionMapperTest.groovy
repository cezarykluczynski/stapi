package com.cezarykluczynski.stapi.auth.api_key.operation.block

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class ApiKeyBlockRelatedExceptionMapperTest extends Specification {

	private ApiKeyBlockRelatedResponseDTOFactory apiKeyBlockRelatedResponseDTOFactoryMock

	private ApiKeyBlockRelatedExceptionMapper apiKeyBlockRelatedExceptionMapper

	void setup() {
		apiKeyBlockRelatedResponseDTOFactoryMock = Mock()
		apiKeyBlockRelatedExceptionMapper = new ApiKeyBlockRelatedExceptionMapper(apiKeyBlockRelatedResponseDTOFactoryMock)
	}

	void "maps KeyDoesNotExistException to ApiKeyBlockRelatedResponseDTO"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyBlockRelatedExceptionMapper.map(new KeyDoesNotExistException())

		then:
		1 * apiKeyBlockRelatedResponseDTOFactoryMock.createFailedWithReason(ApiKeyBlockRelatedResponseDTO.FailReason.KEY_DOES_NOT_EXIST) >>
				apiKeyBlockRelatedResponseDTO
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "maps KeyNotOwnedByAccountException to ApiKeyBlockRelatedResponseDTO"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyBlockRelatedExceptionMapper.map(new KeyNotOwnedByAccountException())

		then:
		1 * apiKeyBlockRelatedResponseDTOFactoryMock.createFailedWithReason(ApiKeyBlockRelatedResponseDTO.FailReason.KEY_NOT_OWNED_BY_ACCOUNT) >>
				apiKeyBlockRelatedResponseDTO
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "throws exception when not mapped exception is passed"() {
		when:
		apiKeyBlockRelatedExceptionMapper.map(new NotMappedException(''))

		then:
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == 'Exception NotMappedException not mapped to ApiKeyBlockRelatedResponseDTO.FailReason'
	}

	class NotMappedException extends ApiKeyException {

		NotMappedException(String message) {
			super(message)
		}

	}

}
