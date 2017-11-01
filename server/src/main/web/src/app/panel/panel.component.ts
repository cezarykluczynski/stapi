import { Component, OnInit } from '@angular/core';

import { RestApiService } from '../rest-api/rest-api.service';
import { WindowReferenceService } from '../window-reference/window-reference.service';

@Component({
	selector: 'app-panel',
	templateUrl: './panel.component.html',
	styleUrls: ['./panel.component.sass']
})
export class PanelComponent implements OnInit {

	private restApiService: RestApiService;
	private windowReferenceService: WindowReferenceService;
	private name: string;
	private authenticated: boolean;

	constructor(restApiService: RestApiService, windowReferenceService: WindowReferenceService) {
		this.restApiService = restApiService;
		this.windowReferenceService = windowReferenceService;
	}

	ngOnInit() {
		this.authenticated = false;
		this.restApiService.getMe().then((response) => {
			this.name = response.name;
			this.authenticated = true;
		}).catch((error) => {
			if (error.status === 403) {
				this.redirectToOAuth();
			}
		});
	}

	getName() {
		return this.name;
	}

	isAuthenticated() {
		return this.authenticated;
	}

	private redirectToOAuth() {
		this.restApiService.getOAuthAuthorizeUrl().then((response) => {
			this.windowReferenceService.getWindow().location.href = response.url;
		});
	}

}
