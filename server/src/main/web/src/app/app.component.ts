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
		return this.domSanitizer.bypassSecurityTrustHtml(`
		<iframe src="https://ghbtns.com/github-btn.html?user=cezarykluczynski&repo=stapi&type=star&count=true&size=large" frameborder="0"
		scrolling="0" width="130px" height="32px"></iframe>`);
	}

}
