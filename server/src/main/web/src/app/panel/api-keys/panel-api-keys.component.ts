import { Component, OnInit } from '@angular/core';

import { PanelApiKeysApi } from './panel-api-keys-api.service';

@Component({
	selector: 'panel-api-keys',
	templateUrl: './panel-api-keys.component.html',
	styleUrls: ['./panel-api-keys.component.sass']
})
export class PanelApiKeysComponent implements OnInit {

	private panelApiKeysApi: PanelApiKeysApi;
	private apiKeys: Array<any>;
	private keysBeingRemoved: Set<number> = new Set();

	constructor(panelApiKeysApi: PanelApiKeysApi) {
		this.panelApiKeysApi = panelApiKeysApi;
	}

	ngOnInit() {
		this.loadApiKeys();
	}

	getApiKeys() {
		return this.apiKeys;
	}

	hasApiKeys() {
		return !!(this.apiKeys && this.apiKeys.length);
	}

	createApiKey() {
		return this.panelApiKeysApi.createApiKey().then((response) => {
			if (response.created) {
				return this.loadApiKeys();
			}
		});
	}

	removeApiKey(apiKeyId: number) {
		return  this.panelApiKeysApi.removeApiKey(apiKeyId).then((response) => {
			if (response.removed) {
				return this.loadApiKeys();
			}
		});
	}

	loadApiKeys() {
		return  this.panelApiKeysApi.getApiKeys().then((response) => {
			this.apiKeys = response.apiKeys;
		});
	}

	askForApiKeyRemoval(apiKeyId) {
		this.keysBeingRemoved.add(apiKeyId);
	}

	doNotRemoveApiKey(apiKeyId) {
		this.keysBeingRemoved.delete(apiKeyId);
	}

	removingApiKey(apiKeyId) {
		return this.keysBeingRemoved.has(apiKeyId);
	}

}
