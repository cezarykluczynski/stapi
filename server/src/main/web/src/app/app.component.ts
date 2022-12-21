import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { ApiDocumentationApi } from './api-documentation/api-documentation-api.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

	private gitHubStargazersCount: any;
	private dataVersion: String;

	constructor(private domSanitizer: DomSanitizer, private featureSwitchApi: FeatureSwitchApi,
				private apiDocumentationApi: ApiDocumentationApi) {
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

}
