import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
	selector: 'app-legal',
	templateUrl: './legal.component.html',
	styleUrls: ['./legal.component.sass']
})
export class LegalComponent implements OnInit {

	private translationVisible: any = false;

	constructor() {}

	ngOnInit() {
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
