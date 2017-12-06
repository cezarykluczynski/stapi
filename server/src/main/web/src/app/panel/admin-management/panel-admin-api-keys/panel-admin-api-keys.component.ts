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
	private pageNumber: Number;
	private pageSize: Number;
	private pager: any;
	private apiKeys: Array<any>;
	private loadingApiKeys: boolean = true;

	constructor(panelAdminManagementApi: PanelAdminManagementApi, notificationsService: NotificationsService) {
		this.panelAdminManagementApi = panelAdminManagementApi;
		this.notificationsService = notificationsService;
	}

	ngOnInit() {
		this.pageSize = 20;
		this.loadApiKeys();
	}

	private loadApiKeys() {
		if (this.pager && this.pageNumber === this.pager.pageNumber) {
			return;
		}

		this.pageNumber = this.pager ? this.pager.pageNumber : 0;
		this.panelAdminManagementApi.getApiKeysPage(this.pageNumber, this.pageSize).then((response) => {
			this.pager = response.pager;
			this.apiKeys = response.apiKeys;
			this.loadingApiKeys = false;
		});
	}

	getApiKeys() {
		return this.apiKeys ? this.apiKeys : [];
	}

	hasApiKeys() {
		return !!this.apiKeys;
	}

}
