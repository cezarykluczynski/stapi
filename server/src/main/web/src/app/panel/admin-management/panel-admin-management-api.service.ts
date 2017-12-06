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

	getApiKeysPage(pageNumber: Number, pageSize: Number) {
		return this.api.panel.admin.apiKeys.get(pageNumber, pageSize);
	}

	private register() {
		this.api.res('panel').res('admin').res('apiKeys').res('block');
		this.api.res('panel').res('admin').res('apiKeys').res('unblock');
	}

}
