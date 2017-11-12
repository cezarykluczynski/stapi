import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class ApiDocumentationApi {

	private api: RestClient;
	private documentation: any;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	loadDocumentation() {
		return this.api.common.documentation.get().then(response => {
			this.documentation = response;
			return this.documentation;
		});
	}

	getDocumentation() {
		return this.documentation;
	}

	private register() {
		this.api.res('common').res('documentation');
	}

}
