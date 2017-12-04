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
	private apiKeyDetails: any;
	private keysBeingRemoved: Set<number> = new Set();
	private keysBeingEdited: Set<number> = new Set();

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
				this.notificationsService.error(this.createApiKeyCreationErrorNotification(response.failReason));
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
		return this.panelApiKeysApi.getApiKeys().then((response) => {
			this.apiKeys = response.apiKeys;
			this.buildApiKeysDetails();
		});
	}

	private buildApiKeysDetails() {
		this.apiKeyDetails = {};
		if (!this.apiKeys) {
			return;
		}
		for (let i = 0; i < this.apiKeys.length; i++) {
			const apiKey = this.apiKeys[i];
			this.apiKeyDetails[apiKey.id] = {
				url: apiKey.url,
				description: apiKey.description
			};
		}
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

	openApiKeyEdit(apiKeyId) {
		this.keysBeingEdited.add(apiKeyId);
	}

	closeApiKeyEdit(apiKeyId) {
		this.keysBeingEdited.delete(apiKeyId);
	}

	editingApiKey(apiKeyId) {
		return this.keysBeingEdited.has(apiKeyId);
	}

	updateApiKey(apiKeyId) {
		this.panelApiKeysApi.saveApiKeyDetails(apiKeyId, this.apiKeyDetails[apiKeyId]).then((response) => {
			this.closeApiKeyEdit(apiKeyId);
			if (response.successful && response.changed) {
				this.notificationsService.success('API key details saved!');
			} else if (response.successful && !response.changed) {
				this.notificationsService.info('There was no changes to save.');
			} else {
				this.notificationsService.error(this.createApiKeyEditErrorNotification(response.failReason));
			}
		});
	}

	private createApiKeyCreationErrorNotification(failReason: string) {
		switch (failReason) {
			case 'TOO_MUCH_KEYS_ALREADY_CREATED':
				return 'You already created the maximal number of API keys.';
			default:
				return 'Uknown error occured. Code: ' + failReason;
		}
	}

	private createApiKeyEditErrorNotification(failReason: string) {
		switch (failReason) {
			case 'URL_TOO_LONG':
				return 'URL is too long. Max length is 256 characters.';
			case 'DESCRIPTION_TOO_LONG':
				return 'Description is too long. Max length is 4000 characters.';
			default:
				return 'Uknown error occured. Code: ' + failReason;
		}
	}

}
