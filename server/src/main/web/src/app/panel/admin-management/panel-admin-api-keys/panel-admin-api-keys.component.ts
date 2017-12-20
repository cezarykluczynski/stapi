import { Component, OnInit } from '@angular/core';

import { NotificationsService } from 'angular2-notifications';

import { PanelAdminManagementApi } from '../panel-admin-management-api.service';

@Component({
	selector: 'panel-admin-api-keys',
	templateUrl: './panel-admin-api-keys.component.html',
	styleUrls: ['./panel-admin-api-keys.component.sass']
})
export class PanelAdminApiKeysComponent implements OnInit {

	private panelAdminManagementApi: PanelAdminManagementApi;
	private notificationsService : NotificationsService;
	private pageNumber: Number = 0;
	public pager: any = {
		totalPages: 0,
		pageNumber: 1,
		pageSize: 0
	};
	public searchCriteria: any = {
		accountId: null,
		gitHubAccountId: null,
		name: null,
		email: null,
		apiKey: null
	};
	private apiKeys: Array<any>;
	private loadingApiKeys: boolean = true;

	constructor(panelAdminManagementApi: PanelAdminManagementApi, notificationsService: NotificationsService) {
		this.panelAdminManagementApi = panelAdminManagementApi;
		this.notificationsService = notificationsService;
	}

	ngOnInit() {
		this.loadApiKeys(true);
	}

	search(event: any) {
		event && event.preventDefault && event.preventDefault();
		this.loadApiKeys(true);
	}

	loadApiKeys(force?: boolean) {
		if (!force && this.pager && this.pageNumber === this.pager.pageNumber - 1) {
			return;
		}

		this.pageNumber = this.pager.pageNumber - 1;
		this.searchCriteria.pageNumber = this.pageNumber;
		this.searchCriteria.accountId = this.searchCriteria.accountId ? this.searchCriteria.accountId : null;
		this.searchCriteria.gitHubAccountId = this.searchCriteria.gitHubAccountId ? this.searchCriteria.gitHubAccountId : null;
		this.searchCriteria.name = this.searchCriteria.name ? this.searchCriteria.name : null;
		this.searchCriteria.email = this.searchCriteria.email ? this.searchCriteria.email : null;
		this.searchCriteria.apiKey = this.searchCriteria.apiKey ? this.searchCriteria.apiKey : null;
		this.panelAdminManagementApi.searchApiKeys(this.searchCriteria).then((response) => {
			this.pager.pageNumber = response.pager.pageNumber + 1;
			this.pager.pageSize = response.pager.pageSize;
			this.pager.totalElements = response.pager.totalElements;
			this.apiKeys = response.apiKeys;
			this.loadingApiKeys = false;
		});
	}

	blockApiKey(accountId: Number, apiKeyId: Number) {
		this.panelAdminManagementApi.blockApiKey({accountId, apiKeyId}).then((response) => {
			this.loadApiKeys(true);
		});
	}

	unblockApiKey(accountId: Number, apiKeyId: Number) {
		this.panelAdminManagementApi.unblockApiKey({accountId, apiKeyId}).then((response) => {
			this.loadApiKeys(true);
		});
	}

	getApiKeys() {
		return this.apiKeys ? this.apiKeys : [];
	}

	hasApiKeys() {
		return !!this.apiKeys;
	}

}
