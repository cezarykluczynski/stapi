import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client/dist/rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class ApiDocumentationApi {

	private api: RestClient;
	private documentation: any;
	private dataVersion: string;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
    this.dataVersion = '';
	}

	loadDocumentation() {
		return (<any> this.api).common.documentation.get().then((response: any) => {
			this.documentation = response;
			return this.documentation;
		});
	}

	loadDataVersion() {
		return (<any> this.api).common.dataVersion.get().then((response: any) => {
			this.dataVersion = response.dataVersion;
			return this.dataVersion;
		});
	}

	getDocumentation() {
		return this.documentation;
	}

	getDataVersion(): string {
		return this.dataVersion;
	}

	private register() {
		this.api.res('common').res('documentation');
		this.api.res('common').res('dataVersion');
	}

}
