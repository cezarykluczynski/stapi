import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { NotificationsService } from 'angular2-notifications';

import { PanelAccountSettingsComponent } from './panel-account-settings.component';
import { PanelAccountApi } from './panel-account-api.service';

class PanelAccountApiMock {
	public removeAccount() {}
}

class NotificationsServiceMock {
	public success() {}
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

	beforeEach(async(() => {
		panelAccountApiMock = new PanelAccountApiMock();
		routerMock = new RouterMock();
		notificationsServiceMock = new NotificationsServiceMock();

		spyOn(panelAccountApiMock, 'removeAccount').and.returnValue(Promise.resolve());
		spyOn(routerMock, 'navigate');
		spyOn(notificationsServiceMock, 'success');

		TestBed.configureTestingModule({
			declarations: [PanelAccountSettingsComponent],
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
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
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
});
