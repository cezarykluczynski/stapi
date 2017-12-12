import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { NotificationsService } from 'angular2-notifications';

import { PanelAdminManagementApi } from '../panel-admin-management-api.service';
import { PanelAdminAccountsComponent } from './panel-admin-accounts.component';

class NotificationsServiceMock {
}

class PanelAdminManagementApiMock {
	public searchAccounts() {}
	public blockApiKey() {}
	public unblockApiKey() {}
}

describe('PanelAdminAccountsComponent', () => {
	let component: PanelAdminAccountsComponent;
	let fixture: ComponentFixture<PanelAdminAccountsComponent>;
	let notificationsServiceMock: NotificationsServiceMock;
	let panelAdminManagementApiMock: PanelAdminManagementApiMock;
	let panelAdminManagementApiMockSearchAccountsSpy: jasmine.Spy;

	const ACCOUNTS = {
		accounts: [
			{
				id: 10
			}
		],
		pager: {
			pageNumber: 0,
			pageSize: 20
		}
	};
	const ACCOUNT_ID = 10;
	const API_KEY_ID = 15;
	const BLOCK_RESULT = {
		successful: true
	};
	const UNBLOCK_RESULT = {
		successful: true
	};

	beforeEach(async(() => {
		notificationsServiceMock = new NotificationsServiceMock();
		panelAdminManagementApiMock = new PanelAdminManagementApiMock();

		panelAdminManagementApiMockSearchAccountsSpy = spyOn(panelAdminManagementApiMock, 'searchAccounts').and.returnValue(Promise.resolve(ACCOUNTS));

		TestBed.configureTestingModule({
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [PanelAdminAccountsComponent],
			providers: [
				{
					provide: NotificationsService,
					useValue: notificationsServiceMock
				},
				{
					provide: PanelAdminManagementApi,
					useValue: panelAdminManagementApiMock
				}
			]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PanelAdminAccountsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('is creates', () => {
		expect(component).toBeTruthy();
	});

	it('loads accounts', () => {
		expect(component.hasAccounts()).toBeFalse();
		expect(component.getAccounts()).toEqual([]);

		fixture.whenStable().then(() => {
			expect(component.getAccounts()).toEqual(ACCOUNTS.accounts);
			expect(component.hasAccounts()).toBeTrue();
		});
	});

	it('searches for accounts', () => {
		fixture.whenStable().then(() => {
			expect(panelAdminManagementApiMockSearchAccountsSpy.calls.count()).toBe(1);
			let event = {
				preventDefault: jasmine.createSpy('preventDefault')
			};

			component.search(event);
			expect(event.preventDefault).toHaveBeenCalled();

			fixture.whenStable().then(() => {
				expect(panelAdminManagementApiMockSearchAccountsSpy.calls.count()).toBe(2);
			});
		});
	});
});
