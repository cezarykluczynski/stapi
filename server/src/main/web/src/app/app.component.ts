import { Component, OnInit } from '@angular/core';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { ApiDocumentationApi } from './api-documentation/api-documentation-api.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

	private baseUrl = 'stapi.co';
	private gitHubStargazersCount: any;
	private dataVersion: string;

	constructor(private featureSwitchApi: FeatureSwitchApi, private apiDocumentationApi: ApiDocumentationApi) {
		this.dataVersion = '';
	}

	ngOnInit() {
		this.dataVersion = this.apiDocumentationApi.getDataVersion();
		this.gitHubStargazersCount = this.apiDocumentationApi.getGitHubStargazersCount();
	}

	hasTocAndPP() {
		return this.featureSwitchApi.isEnabled('TOS_AND_PP');
	}

	getGitHubStarsCount() {
		return this.gitHubStargazersCount;
	}

	hasGitHubStargazersCount() {
		return !!this.gitHubStargazersCount;
	}

	hasHttpsNotice() {
		return location.href.startsWith('http://' + this.baseUrl);
	}

	getHttpsVersionUrl() {
		return location.href.replace('http://' + this.baseUrl, 'https://' + this.baseUrl);
	}

}
