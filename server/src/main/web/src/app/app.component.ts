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

	hasDataVersion() {
		return this.dataVersion != null && this.dataVersion.length;
	}

	getDataVersion() {
		const dataVersionParts = this.dataVersion.split('-');
		const year = dataVersionParts[0];
		const month = this.getMonth(Number.parseInt(dataVersionParts[1]));
		return month + ' ' + year;
	}

	getMonth(number: number) {
		switch (number) {
			case 1: return 'January';
			case 2: return 'February';
			case 3: return 'March';
			case 4: return 'April';
			case 5: return 'May';
			case 6: return 'June';
			case 7: return 'July';
			case 8: return 'August';
			case 9: return 'September';
			case 10: return 'October';
			case 11: return 'November';
			case 12: return 'December';
		}
		return '';
	}

}
