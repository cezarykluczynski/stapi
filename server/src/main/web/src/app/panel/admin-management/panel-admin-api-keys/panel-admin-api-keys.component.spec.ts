import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { NotificationsService } from 'angular2-notifications';

import { PanelAdminManagementApi } from '../panel-admin-management-api.service';
import { PanelAdminApiKeysComponent } from './panel-admin-api-keys.component';

class NotificationsServiceMock {
}

class PanelAdminManagementApiMock {
	public getApiKeysPage() {}
}

describe('PanelAdminApiKeysComponent', () => {
	let component: PanelAdminApiKeysComponent;
	let fixture: ComponentFixture<PanelAdminApiKeysComponent>;
	let notificationsServiceMock: NotificationsServiceMock;
	let panelAdminManagementApiMock: PanelAdminManagementApiMock;

	const API_KEYS = {
		apiKeys: [
			{
				id: 10
			}
		],
		pager: {
			pageNumber: 0,
			pageSize: 20
		}
	};

	beforeEach(async(() => {
		notificationsServiceMock = new NotificationsServiceMock();
		panelAdminManagementApiMock = new PanelAdminManagementApiMock();

		spyOn(panelAdminManagementApiMock, 'getApiKeysPage').and.returnValue(Promise.resolve(API_KEYS));

		TestBed.configureTestingModule({
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [PanelAdminApiKeysComponent],
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
		fixture = TestBed.createComponent(PanelAdminApiKeysComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should load api keys', () => {
		expect(component.hasApiKeys()).toBeFalse();
		expect(component.getApiKeys()).toEqual([]);

		fixture.whenStable().then(() => {
			expect(component.getApiKeys()).toEqual(API_KEYS.apiKeys);
			expect(component.hasApiKeys()).toBeTrue();
		});
	});
});
