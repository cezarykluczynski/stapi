import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { NotificationsService } from 'angular2-notifications';

import { PanelAccountApi } from './panel-account-api.service';

@Component({
	selector: 'panel-account-settings',
	templateUrl: './panel-account-settings.component.html',
	styleUrls: ['./panel-account-settings.component.sass']
})
export class PanelAccountSettingsComponent implements OnInit {

	private panelAccountApi: PanelAccountApi;
	private router: Router;
	private notificationsService: NotificationsService;
	private accountIsBeingRemoved: boolean;
	private consents: Array<any>;
	private ownConsents: Array<string>;
	public selectedConsents: any = {};
	public basicDataEditable: any;


	@Input()
	public basicData: any;

	constructor(panelAccountApi: PanelAccountApi, router: Router, notificationsService: NotificationsService) {
		this.panelAccountApi = panelAccountApi;
		this.router = router;
		this.notificationsService = notificationsService;
	}

	ngOnInit() {
		this.basicDataEditable = {...this.basicData};
		Promise.all([this.panelAccountApi.getConsents().then((response) => {
			this.consents = response;
		}),
		this.panelAccountApi.getOwnConsents().then((response) => {
			this.ownConsents = response.consentCodes;
		})]).then(() => {
			this.buildConsents();
		});
	}

	buildConsents() {
		this.consents.forEach((consent) => {
			this.selectedConsents[consent.code] = this.hasConsentSelected(consent.code);
		});
	}

	hasConsentsLoaded() {
		return !!(this.consents && this.ownConsents);
	}

	getConsents() {
		return this.consents;
	}

	hasConsentSelected(consentCode: string) {
		return this.ownConsents.indexOf(consentCode) > -1;
	}

	getConsentDescription(consentCode: string) {
		switch (consentCode) {
			case 'TECHNICAL_MAILING':
				return 'I agree to receive technical mailing, mainly information about new STAPI versions, '
					+ 'scheduled maintenance, outages, and details about limitation on access to STAPI.';
			default:
				'Unknown consent...'
		}
	}

	updateOwnConsents() {
		this.ownConsents = [];
		for (let key in this.selectedConsents) {
			if (this.selectedConsents.hasOwnProperty(key) && this.selectedConsents[key]) {
				this.ownConsents.push(key);
			}
		}
		this.panelAccountApi.updateOwnConsents(this.ownConsents).then((response) => {
			if (response.successful) {
				this.notificationsService.success('Consents saved!');
			} else {
				this.notificationsService.error('Consent could not be saved. Error code: ' + response.failReason);
			}
		});
	}

	askForAccountRemoval() {
		this.accountIsBeingRemoved = true;
	}

	cancelAccountRemoval() {
		this.accountIsBeingRemoved = false;
	}

	isAccountBeingRemoved() {
		return this.accountIsBeingRemoved;
	}

	removeAccount() {
		this.panelAccountApi.removeAccount().then(() => {
			this.router.navigate(['/']);
			this.notificationsService.success('You account was removed. Bye!');
		});
	}

	updateBasicData() {
		this.panelAccountApi.updateBasicData(this.basicDataEditable)
			.then((response) => {
				if (response.successful && response.changed) {
					this.notificationsService.success('Your personal information was updated.');
					this.basicData.name = this.basicDataEditable.name;
				} else if (response.successful && !response.changed) {
					this.notificationsService.info('There was no changes to save.');
				} else {
					this.notificationsService.error(this.createErrorNotification(response.failReason));
				}
			});
	}

	private createErrorNotification(failReason: string) {
		switch (failReason) {
			case 'INVALID_NAME':
				return 'The given name is invalid.';
			case 'INVALID_EMAIL':
				return 'The given e-mail address is invalid.';
			default:
				return 'Uknown error occured. Code: ' + failReason;
		}
	}

}
