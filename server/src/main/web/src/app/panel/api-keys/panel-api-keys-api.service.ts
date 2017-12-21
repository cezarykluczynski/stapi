import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';

@Injectable()
export class PanelApiKeysApi {

	private api: RestClient;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	createApiKey() {
		return this.api.common.panel.apiKeys.post({}, 'application/x-www-form-urlencoded');
	}

	getApiKeys() {
		return this.api.common.panel.apiKeys.get();
	}

	removeApiKey(apiKeyId: number) {
		return this.api.common.panel.apiKeys(apiKeyId).delete();
	}

	saveApiKeyDetails(apiKeyId: number, details: any) {
		return this.api.common.panel.apiKeys(apiKeyId).post(details, 'application/x-www-form-urlencoded');
	}

	private register() {
		this.api.res('common').res('panel').res('apiKeys');
	}

}
