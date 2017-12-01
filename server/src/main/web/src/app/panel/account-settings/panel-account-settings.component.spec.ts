import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { NotificationsService } from 'angular2-notifications';

import { PanelAccountSettingsComponent } from './panel-account-settings.component';
import { PanelAccountApi } from './panel-account-api.service';

class PanelAccountApiMock {
	public removeAccount() {}
	public updateBasicData() {}
	public getOwnConsents() {}
	public updateOwnConsents() {}
	public getConsents() {}
}

class NotificationsServiceMock {
	public success() {}
	public info() {}
	public error() {}
}

class RouterMock {
	public navigate() {}
}

describe('PanelAccountSettingsComponent', () => {
	let component: PanelAccountSettingsComponent;
	let fixture: ComponentFixture<PanelAccountSettingsComponent>;
	let panelAccountApiMock: PanelAccountApiMock;
	let routerMock: RouterMock;
	let notificationsServiceMock: NotificationsServiceMock;
	const NAME_1 = 'NAME_1';
	const NAME_2 = 'NAME_2';
	const EMAIL_1 = 'EMAIL_1';
	const EMAIL_2 = 'EMAIL_2';
	let BASIC_DATA;
	const SUCCESSFUL_UPDATE = {
		successful: true,
		changed: true
	};
	const UNSUCCESSFUL_UPDATE_INVALID_NAME = {
		successful: false,
		changed: false,
		failReason: 'INVALID_NAME'
	};
	const UNSUCCESSFUL_UPDATE_INVALID_EMAIL = {
		successful: false,
		changed: false,
		failReason: 'INVALID_EMAIL'
	};
	const UNSUCCESSFUL_UPDATE_UNKNOWN_ERROR = {
		successful: false,
		changed: false,
		failReason: 'UNKNOWN_ERROR'
	};
	const UNCHANGED_UPDATE = {
		successful: true,
		changed: false
	};
	const ownConsentCodes = ['CONSENT_1'];
	const consentCodes = ['CONSENT_1'];
	let CONSENTS_UPDATE = {
		successful: true
	};

	beforeEach(async(() => {
		panelAccountApiMock = new PanelAccountApiMock();
		routerMock = new RouterMock();
		notificationsServiceMock = new NotificationsServiceMock();

		spyOn(panelAccountApiMock, 'removeAccount').and.returnValue(Promise.resolve());
		spyOn(panelAccountApiMock, 'getOwnConsents').and.returnValue(Promise.resolve({
			consentCodes: consentCodes
		}));
		spyOn(panelAccountApiMock, 'getConsents').and.returnValue(Promise.resolve(consentCodes));
		spyOn(panelAccountApiMock, 'updateOwnConsents').and.returnValue(Promise.resolve(CONSENTS_UPDATE));
		spyOn(routerMock, 'navigate');
		spyOn(notificationsServiceMock, 'success');
		spyOn(notificationsServiceMock, 'info');
		spyOn(notificationsServiceMock, 'error');

		BASIC_DATA = {
			name: NAME_1,
			email: EMAIL_1
		};

		TestBed.configureTestingModule({
			declarations: [PanelAccountSettingsComponent],
			imports: [FormsModule],
			providers: [
				{
					provide: PanelAccountApi,
					useValue: panelAccountApiMock
				},
				{
					provide: Router,
					useValue: routerMock
				},
				{
					provide: NotificationsService,
					useValue: notificationsServiceMock
				}
			]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PanelAccountSettingsComponent);
		component = fixture.componentInstance;
		component.basicData = BASIC_DATA;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should fetch consents on initialization', () => {
		expect(component.hasConsentsLoaded()).toBeFalse();

		fixture.whenStable().then(() => {
			expect(component.getConsents()).toBe(consentCodes);
			expect(component.hasConsentSelected(consentCodes[0])).toBeTrue();
		});
	});

	it('ask for account removal, then reverts state', () => {
		fixture.whenStable().then(() => {
			component.askForAccountRemoval();
			expect(component.isAccountBeingRemoved()).toBeTrue();

			component.cancelAccountRemoval();
			expect(component.isAccountBeingRemoved()).toBeFalse();
		});
	});

	it('ask for account removal, then removes account', () => {
		fixture.whenStable().then(() => {
			component.askForAccountRemoval();
			expect(component.isAccountBeingRemoved()).toBeTrue();

			component.removeAccount();
			expect(component.isAccountBeingRemoved()).toBeTrue();

			fixture.whenStable().then(() => {
				expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
				expect(notificationsServiceMock.success).toHaveBeenCalledWith('You account was removed. Bye!');
			});
		});
	});

	it('updates basic data', () => {
		spyOn(panelAccountApiMock, 'updateBasicData').and.returnValue(Promise.resolve(SUCCESSFUL_UPDATE));

		component.basicDataEditable.name = NAME_2;

		fixture.whenStable().then(() => {
			component.updateBasicData();

			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.success).toHaveBeenCalledWith('Your personal information was updated.');
				expect(component.basicData.name).toBe(NAME_2);
			});
		});
	});

	it('makes no update when there were no changes', () => {
		spyOn(panelAccountApiMock, 'updateBasicData').and.returnValue(Promise.resolve(UNCHANGED_UPDATE));

		fixture.whenStable().then(() => {
			component.updateBasicData();

			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.info).toHaveBeenCalledWith('There was no changes to save.');
				expect(component.basicData.name).toBe(NAME_1);
			});
		});
	});

	it('maps invalid name error to notification', () => {
		spyOn(panelAccountApiMock, 'updateBasicData').and.returnValue(Promise.resolve(UNSUCCESSFUL_UPDATE_INVALID_NAME));

		fixture.whenStable().then(() => {
			component.updateBasicData();

			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.error).toHaveBeenCalledWith('The given name is invalid.');
				expect(component.basicData.name).toBe(NAME_1);
			});
		});
	});

	it('maps invalid e-mail error to notification', () => {
		spyOn(panelAccountApiMock, 'updateBasicData').and.returnValue(Promise.resolve(UNSUCCESSFUL_UPDATE_INVALID_EMAIL));

		fixture.whenStable().then(() => {
			component.updateBasicData();

			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.error).toHaveBeenCalledWith('The given e-mail address is invalid.');
				expect(component.basicData.name).toBe(NAME_1);
			});
		});
	});

	it('maps unknown error to notification', () => {
		spyOn(panelAccountApiMock, 'updateBasicData').and.returnValue(Promise.resolve(UNSUCCESSFUL_UPDATE_UNKNOWN_ERROR));

		fixture.whenStable().then(() => {
			component.updateBasicData();

			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.error).toHaveBeenCalledWith('Uknown error occured. Code: UNKNOWN_ERROR');
				expect(component.basicData.name).toBe(NAME_1);
			});
		});
	});

	it('unselects consent, then saves own consents', () => {
		fixture.whenStable().then(() => {
			component.selectedConsents['TECHNICAL_MAILING'] = false;
			component.updateOwnConsents();

			fixture.whenStable().then(() => {
				expect(panelAccountApiMock.updateOwnConsents).toHaveBeenCalled();
				expect(notificationsServiceMock.success).toHaveBeenCalledWith('Consents saved!');
			});
		});
	});
});
