import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';

import { RestApiService } from '../rest-api/rest-api.service';
import { PanelApi } from './panel-api.service';
import { WindowReferenceService } from '../window-reference/window-reference.service';
import { PanelApiKeysComponent } from './api-keys/panel-api-keys.component';

enum PanelView {
	API_KEYS,
	ACCOUNT_SETTINGS,
	ADMIN_API_KEYS,
	ADMIN_ACCOUNTS
}

enum ApplicationPermission {
	API_KEY_MANAGEMENT,
	ADMIN_MANAGEMENT
}

@Component({
	selector: 'panel',
	templateUrl: './panel.component.html',
	styleUrls: ['./panel.component.sass']
})
export class PanelComponent implements OnInit {

	private panelApi: PanelApi;
	private windowReferenceService: WindowReferenceService;
	private restApiService: RestApiService;
	private name: string;
	private permissions: Set<ApplicationPermission>;
	private authenticated: boolean = false;
	private redirecting: boolean = false;
	private authenticationRequired: boolean = false;
	private hasLegalConsents: boolean = false;
	private activeView: PanelView = PanelView.API_KEYS;
	private basicData: any;

	constructor(panelApi: PanelApi, windowReferenceService: WindowReferenceService, restApiService: RestApiService) {
		this.panelApi = panelApi;
		this.windowReferenceService = windowReferenceService;
		this.restApiService = restApiService;
	}

	ngOnInit() {
		this.restApiService.getApi().on('error', (error) => {
			if (error.status === 403) {
				this.authenticated = false;
				this.authenticationRequired = true;
			}
		});

		this.panelApi.getMe().then((response) => {
			this.authenticated = true;
			this.basicData = {
				name: response.name,
				email: response.email
			};
			this.permissions = new Set();
			response.permissions.forEach((permission) => {
				let applicationPermission: ApplicationPermission = ApplicationPermission[<string>permission];
				this.permissions.add(applicationPermission);
			});
		});
	}

	getName() {
		return this.basicData ? this.basicData.name : 'stranger';
	}

	buttonIsDisabled() {
		console.log('buttonIsDisabled', this.hasLegalConsents, this.isRedirecting());
		return !this.hasLegalConsents || this.isRedirecting();
	}

	getButtonLabel() {
		return this.isRedirecting() ? 'Redirecting to GitHub...' : 'Authenticate with GitHub';
	}

	consentChange(event) {
		this.hasLegalConsents = event.target.checked;
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

	showApiKeys() {
		this.activeView = PanelView.API_KEYS;
	}

	showAccountSettings() {
		this.activeView = PanelView.ACCOUNT_SETTINGS;
	}

	showAdminApiKeys() {
		this.activeView = PanelView.ADMIN_API_KEYS;
	}

	showAdminAccounts() {
		this.activeView = PanelView.ADMIN_ACCOUNTS;
	}

	apiKeysIsVisible() {
		return this.activeView === PanelView.API_KEYS;
	}

	accountSettingsIsVisible() {
		return this.activeView === PanelView.ACCOUNT_SETTINGS;
	}

	adminApiKeysIsVisible() {
		return this.activeView === PanelView.ADMIN_API_KEYS;
	}

	adminAccountsIsVisible() {
		return this.activeView === PanelView.ADMIN_ACCOUNTS;
	}

	hasAdminManagementPermission() {
		return this.permissions.has(ApplicationPermission.ADMIN_MANAGEMENT);
	}

	redirectToOAuth() {
		this.redirecting = true;
		this.panelApi.getOAuthAuthorizeUrl().then((response) => {
			this.windowReferenceService.getWindow().location.href = response.url;
		});
	}

}
