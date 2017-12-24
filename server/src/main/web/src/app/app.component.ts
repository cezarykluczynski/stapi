import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent {

	constructor(private domSanitizer: DomSanitizer, private featureSwitchApi: FeatureSwitchApi) {
	}

	panelIsEnabled() {
		return this.featureSwitchApi.isEnabled('PANEL');
	}

	getGitHubStar() {
		return this.domSanitizer.bypassSecurityTrustHtml(`<a class="github-button"
			href="https://github.com/cezarykluczynski/stapi"
			data-size="large" data-show-count="true" aria-label="Star cezarykluczynski/stapi on GitHub">Star</a>`);
	}

}
