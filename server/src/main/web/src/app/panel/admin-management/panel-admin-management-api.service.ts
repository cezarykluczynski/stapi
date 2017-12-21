import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';

@Injectable()
export class PanelAdminManagementApi {

	private api: RestClient;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	searchApiKeys(searchCriteria: any) {
		return this.api.common.panel.admin.apiKeys.post(searchCriteria);
	}

	blockApiKey(block: any) {
		return this.api.common.panel.admin.apiKeys.block.post(block, 'application/x-www-form-urlencoded');
	}

	unblockApiKey(unblock: any) {
		return this.api.common.panel.admin.apiKeys.unblock.post(unblock, 'application/x-www-form-urlencoded');
	}

	searchAccounts(searchCriteria: any) {
		return this.api.common.panel.admin.accounts.post(searchCriteria);
	}

	blockAccount(block: any) {
		return this.api.common.panel.admin.accounts.block.post(block, 'application/x-www-form-urlencoded');
	}

	unblockAccount(unblock: any) {
		return this.api.common.panel.admin.accounts.unblock.post(unblock, 'application/x-www-form-urlencoded');
	}

	private register() {
		this.api.res('common').res('panel').res('admin').res('apiKeys').res('block');
		this.api.res('common').res('panel').res('admin').res('apiKeys').res('unblock');
		this.api.res('common').res('panel').res('admin').res('accounts');
	}

}
