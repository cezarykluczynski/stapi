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
	private authenticated: boolean = false;
	private redirecting: boolean = false;
	private authenticationRequired: boolean = false;

	constructor(restApiService: RestApiService, windowReferenceService: WindowReferenceService) {
		this.restApiService = restApiService;
		this.windowReferenceService = windowReferenceService;
	}

	ngOnInit() {
		this.restApiService.getMe().then((response) => {
			this.name = response.name;
			this.authenticated = true;
		}).catch((error) => {
			if (error.status === 403) {
				this.authenticationRequired = true;
			}
		});
	}

	getName() {
		return this.name;
	}

	getButtonLabel() {
		return this.isRedirecting() ? 'Redirecting to GitHub...' : 'Authenticate with GitHub';
	}

	isRedirecting() {
		return this.redirecting;
	}

	isAuthenticated() {
		return this.authenticated;
	}

	isAuthenticationRequired() {
		return this.authenticationRequired;
	}

	redirectToOAuth() {
		this.redirecting = true;
		this.restApiService.getOAuthAuthorizeUrl().then((response) => {
			this.windowReferenceService.getWindow().location.href = response.url;
		});
	}

}
