import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';

declare var $: any;

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent {

	constructor(private domSanitizer: DomSanitizer, private featureSwitchApi: FeatureSwitchApi) {
	}

	panelIsEnabled() {
		return this.featureSwitchApi.isEnabled('USER_PANEL') || this.featureSwitchApi.isEnabled('ADMIN_PANEL');
	}

}
