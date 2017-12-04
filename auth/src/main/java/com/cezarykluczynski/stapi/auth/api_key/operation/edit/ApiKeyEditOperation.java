package com.cezarykluczynski.stapi.auth.api_key.operation.edit;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiKeyEditOperation {

	private final ApiKeyRepository apiKeyRepository;

	private final ApiKeyEditResponseDTOFactory apiKeyEditResponseDTOFactory;

	private final ApiKeyEditValidator apiKeyEditValidator;

	public ApiKeyEditOperation(ApiKeyRepository apiKeyRepository, ApiKeyEditResponseDTOFactory apiKeyEditResponseDTOFactory,
			ApiKeyEditValidator apiKeyEditValidator) {
		this.apiKeyRepository = apiKeyRepository;
		this.apiKeyEditResponseDTOFactory = apiKeyEditResponseDTOFactory;
		this.apiKeyEditValidator = apiKeyEditValidator;
	}

	@Transactional
	public ApiKeyEditResponseDTO execute(Long accountId, ApiKeyDetailsDTO apiKeyDetailsDTO) {
		Preconditions.checkNotNull(accountId, "Account ID cannot be null");
		Preconditions.checkNotNull(apiKeyDetailsDTO, "ApiKeyDetailsDTO cannot be null");
		Long apiKeyId = Preconditions.checkNotNull(apiKeyDetailsDTO.getApiKeyId(), "API key id cannot be null");

		ApiKey apiKey = apiKeyRepository.findAllByAccountId(accountId).stream()
				.filter(key -> apiKeyId.equals(key.getId()))
				.findFirst()
				.orElseThrow(() -> new StapiRuntimeException(String.format("Cannot find key %s", apiKeyId)));

		String currentUrl = StringUtils.defaultIfBlank(apiKey.getUrl(), StringUtils.EMPTY);
		String currentDescription = StringUtils.defaultIfBlank(apiKey.getDescription(), StringUtils.EMPTY);
		String incomingUrl = StringUtils.defaultIfBlank(apiKeyDetailsDTO.getUrl(), StringUtils.EMPTY);
		String incomingDescription = StringUtils.defaultIfBlank(apiKeyDetailsDTO.getDescription(), StringUtils.EMPTY);
		boolean urlsEquals = StringUtils.equals(currentUrl, incomingUrl);
		boolean descriptionsEquals = StringUtils.equals(currentDescription, incomingDescription);

		if (urlsEquals && descriptionsEquals) {
			return apiKeyEditResponseDTOFactory.createUnchanged();
		}

		if (!urlsEquals && !apiKeyEditValidator.isUrlShortEnough(incomingUrl)) {
			return apiKeyEditResponseDTOFactory.createUnsuccessful(ApiKeyEditResponseDTO.FailReason.URL_TOO_LONG);
		}

		if (!descriptionsEquals && !apiKeyEditValidator.isDescriptionShortEnough(incomingDescription)) {
			return apiKeyEditResponseDTOFactory.createUnsuccessful(ApiKeyEditResponseDTO.FailReason.DESCRIPTION_TOO_LONG);
		}

		apiKey.setUrl(incomingUrl);
		apiKey.setDescription(incomingDescription);

		return apiKeyEditResponseDTOFactory.createSuccessful();
	}

}
