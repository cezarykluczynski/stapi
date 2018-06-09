import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { PanelApi } from './panel/panel-api.service';

declare var $: any;

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

	private gitHubProjectDetails: any;

	constructor(private domSanitizer: DomSanitizer, private featureSwitchApi: FeatureSwitchApi, private panelApi: PanelApi) {
	}

	ngOnInit() {
		this.panelApi.getGitHubProjectDetails().then((response) => {
			this.gitHubProjectDetails = response;
		});
	}

	panelIsEnabled() {
		return this.featureSwitchApi.isEnabled('USER_PANEL') || this.featureSwitchApi.isEnabled('ADMIN_PANEL');
	}

	getGitHubStarsCount() {
		return this.gitHubProjectDetails.stargazersCount;
	}

	hasGitHubProjectDetails() {
		return !!this.gitHubProjectDetails;
	}

}
