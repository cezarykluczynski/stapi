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
