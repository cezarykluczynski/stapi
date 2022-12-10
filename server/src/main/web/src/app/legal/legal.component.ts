import { Component, OnInit } from '@angular/core';
import {FeatureSwitchApi} from '../feature-switch/feature-switch-api.service';
import {Router} from '@angular/router';

declare var $: any;

@Component({
	selector: 'app-legal',
	templateUrl: './legal.component.html',
	styleUrls: ['./legal.component.sass']
})
export class LegalComponent implements OnInit {

	private translationVisible: any = false;

	constructor(private featureSwitchApi: FeatureSwitchApi, private router: Router) {}

	ngOnInit() {
		if (!this.featureSwitchApi.isEnabled('TOS_AND_PP')) {
			this.router.navigate(['/']);
			return;
		}
		$('.legal-holder').appendTo($('.legal-wrapper'));
		$('.legal-holder-translation').appendTo($('.legal-wrapper-translation'));
	}

	toggleTranslation() {
		this.translationVisible = !this.translationVisible;
	}

	showTranslation() {
		return this.translationVisible;
	}

}
