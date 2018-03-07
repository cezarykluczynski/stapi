import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';

declare var $: any;

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.sass']
})
export class AppComponent  {

	constructor(private domSanitizer: DomSanitizer, private featureSwitchApi: FeatureSwitchApi) {
	}

	panelIsEnabled() {
		return this.featureSwitchApi.isEnabled('USER_PANEL') || this.featureSwitchApi.isEnabled('ADMIN_PANEL');
	}

	ngOnInit() {
		if ($('body > div.stars').length) {
			return;
		}

		$('<div class="stars"><iframe src="https://ghbtns.com/github-btn.html?user=cezarykluczynski&repo=stapi&type=star&count=true&size=large" ' +
			'frameborder="0" scrolling="0" width="130px" height="32px"></iframe></div>').prependTo('body');
	}

}
