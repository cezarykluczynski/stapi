import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class ApiDocumentationApi {

	private api: RestClient;
	private documentation: any;
	private dataVersion: String;
	private stargazersCount: Number;

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

	loadDataVersion() {
		return this.api.common.dataVersion.get().then(response => {
			this.dataVersion = response.dataVersion;
			return this.dataVersion;
		});
	}

	loadGitHubProjectDetails() {
		return this.api.common.github.projectDetails.get().then(response => {
			this.stargazersCount = response.stargazersCount;
			return this.stargazersCount;
		});
	}

	getGitHubStargazersCount() {
		return this.stargazersCount;
	}

	getDocumentation() {
		return this.documentation;
	}

	getDataVersion() {
		return this.dataVersion;
	}

	private register() {
		this.api.res('common').res('documentation');
		this.api.res('common').res('dataVersion');
		this.api.res('common').res('github').res('projectDetails');
	}

}
