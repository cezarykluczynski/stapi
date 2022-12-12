import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { PanelApi } from './panel/panel-api.service';
import {ApiDocumentationApi} from './api-documentation/api-documentation-api.service';

declare var $: any;

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

	private gitHubProjectDetails: any;
	private dataVersion: String;

	constructor(private domSanitizer: DomSanitizer, private featureSwitchApi: FeatureSwitchApi, private panelApi: PanelApi,
				private apiDocumentationApi: ApiDocumentationApi) {
	}

	ngOnInit() {
		this.panelApi.getGitHubProjectDetails().then((response) => {
			this.gitHubProjectDetails = response;
		});
		this.dataVersion = this.apiDocumentationApi.getDataVersion();
	}

	panelIsEnabled() {
		return this.featureSwitchApi.isEnabled('USER_PANEL') || this.featureSwitchApi.isEnabled('ADMIN_PANEL');
	}

	hasTocAndPP() {
		return this.featureSwitchApi.isEnabled('TOS_AND_PP');
	}

	getGitHubStarsCount() {
		return this.gitHubProjectDetails.stargazersCount;
	}

	hasGitHubProjectDetails() {
		return !!this.gitHubProjectDetails;
	}

}
