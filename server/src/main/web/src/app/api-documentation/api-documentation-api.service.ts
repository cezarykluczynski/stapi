import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client/dist/rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class ApiDocumentationApi {

	private api: RestClient;
	private documentation: any;
	private dataVersion: string;
	private stargazersCount: number;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
    this.dataVersion = '';
    this.stargazersCount = 0;
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

	loadGitHubProjectDetails() {
		return (<any> this.api).common.github.projectDetails.get().then((response: any) => {
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

	getDataVersion(): string {
		return this.dataVersion;
	}

	private register() {
		this.api.res('common').res('documentation');
		this.api.res('common').res('dataVersion');
		this.api.res('common').res('github').res('projectDetails');
	}

}
