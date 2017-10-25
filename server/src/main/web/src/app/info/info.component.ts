import { Component, OnInit, Input } from '@angular/core';

@Component({
	/* tslint:disable */
	selector: 'info',
	/* tslint:enable */
	templateUrl: './info.component.html',
	styleUrls: ['./info.component.sass']
})
export class InfoComponent implements OnInit {

	@Input() message?: string;

	constructor() {}

	ngOnInit() {}

	getMessage() {
		return this.message;
	}

}
