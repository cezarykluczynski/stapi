import { Component, OnInit } from '@angular/core';

import { NotificationsService } from 'angular2-notifications';

import { PanelAdminManagementApi } from '../panel-admin-management-api.service';

@Component({
	selector: 'panel-admin-accounts',
	templateUrl: './panel-admin-accounts.component.html',
	styleUrls: ['./panel-admin-accounts.component.sass']
})
export class PanelAdminAccountsComponent implements OnInit {

	private panelAdminManagementApi: PanelAdminManagementApi;
	private notificationsService : NotificationsService;
	private pageNumber: Number = 0;
	public pager: any = {
		totalPages: 0,
		pageNumber: 1,
		pageSize: 0
	};
	public searchCriteria: any = {
		id: null,
		gitHubAccountId: null,
		name: null,
		email: null
	};
	private accounts: Array<any>;
	private loadingApiKeys: boolean = true;

	constructor(panelAdminManagementApi: PanelAdminManagementApi, notificationsService: NotificationsService) {
		this.panelAdminManagementApi = panelAdminManagementApi;
		this.notificationsService = notificationsService;
	}

	ngOnInit() {
		this.loadAccounts(true);
	}

	search(event: any) {
		event && event.preventDefault && event.preventDefault();
		this.loadAccounts(true);
	}

	loadAccounts(force?: boolean) {
		if (!force && this.pager && this.pageNumber === this.pager.pageNumber - 1) {
			return;
		}

		this.pageNumber = this.pager.pageNumber - 1;
		this.searchCriteria.pageNumber = this.pageNumber;
		this.searchCriteria.id = this.searchCriteria.id ? this.searchCriteria.id : null;
		this.searchCriteria.gitHubAccountId = this.searchCriteria.gitHubAccountId ? this.searchCriteria.gitHubAccountId : null;
		this.searchCriteria.name = this.searchCriteria.name ? this.searchCriteria.name : null;
		this.searchCriteria.email = this.searchCriteria.email ? this.searchCriteria.email : null;
		this.panelAdminManagementApi.searchAccounts(this.searchCriteria).then((response) => {
			this.pager.pageNumber = response.pager.pageNumber + 1;
			this.pager.pageSize = response.pager.pageSize;
			this.pager.totalElements = response.pager.totalElements;
			this.accounts = response.accounts;
			this.loadingApiKeys = false;
		});
	}

	getAccounts() {
		return this.accounts ? this.accounts : [];
	}

	hasAccounts() {
		return !!this.accounts;
	}

}
