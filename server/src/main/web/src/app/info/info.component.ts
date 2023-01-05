import { Component, Input } from '@angular/core';

@Component({
	selector: 'app-info',
	templateUrl: './info.component.html',
	styleUrls: ['./info.component.sass']
})
export class InfoComponent {

	@Input() message?: string;

	getMessage() {
		return this.message;
	}

}
