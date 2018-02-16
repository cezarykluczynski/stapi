import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
	selector: 'app-legal',
	templateUrl: './legal.component.html',
	styleUrls: ['./legal.component.sass']
})
export class LegalComponent implements OnInit {

	constructor() {}

	ngOnInit() {
		console.log('init');
		$('.legal-holder').appendTo($('.legal-wrapper'));
	}

}
