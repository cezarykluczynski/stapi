import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { NotificationsService } from 'angular2-notifications';

import { PanelAccountApi } from './panel-account-api.service';

@Component({
	selector: 'panel-account-settings',
	templateUrl: './panel-account-settings.component.html',
	styleUrls: ['./panel-account-settings.component.sass']
})
export class PanelAccountSettingsComponent {

	private panelAccountApi: PanelAccountApi;
	private router: Router;
	private notificationsService: NotificationsService;
	private accountIsBeingRemoved: boolean;

	constructor(panelAccountApi: PanelAccountApi, router: Router, notificationsService: NotificationsService) {
		this.panelAccountApi = panelAccountApi;
		this.router = router;
		this.notificationsService = notificationsService;
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

}
