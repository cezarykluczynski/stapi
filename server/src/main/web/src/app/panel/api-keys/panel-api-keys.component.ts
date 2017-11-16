import { Component, OnInit } from '@angular/core';

import { NotificationsService } from 'angular2-notifications';

import { PanelApiKeysApi } from './panel-api-keys-api.service';

@Component({
	selector: 'panel-api-keys',
	templateUrl: './panel-api-keys.component.html',
	styleUrls: ['./panel-api-keys.component.sass']
})
export class PanelApiKeysComponent implements OnInit {

	private panelApiKeysApi: PanelApiKeysApi;
	private notificationsService : NotificationsService;
	private apiKeys: Array<any>;
	private keysBeingRemoved: Set<number> = new Set();

	constructor(panelApiKeysApi: PanelApiKeysApi, notificationsService: NotificationsService) {
		this.panelApiKeysApi = panelApiKeysApi;
		this.notificationsService = notificationsService;
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
			} else {
				this.notificationsService.error(this.createErrorNotification(response.failReason));
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

	private createErrorNotification(failReason: string) {
		switch (failReason) {
			case 'TOO_MUCH_KEYS_ALREADY_CREATED':
				return 'You already created the maximal number of API keys.';
			default:
				return 'Uknown error occured. Code: ' + failReason;
		}
	}

}
