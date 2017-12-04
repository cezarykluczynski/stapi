import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { NotificationsService } from 'angular2-notifications';

import { PanelApiKeysComponent } from './panel-api-keys.component';
import { PanelApiKeysApi } from './panel-api-keys-api.service';

class PanelApiKeysApiMock {
	public createApiKey() {}
	public removeApiKey() {}
	public getApiKeys() {}
	public saveApiKeyDetails() {}
}

class NotificationsServiceMock {
	public success() {}
	public info() {}
	public error() {}
}

describe('PanelApiKeysComponent', () => {
	let component: PanelApiKeysComponent;
	let fixture: ComponentFixture<PanelApiKeysComponent>;
	let panelApiKeysApiMock: PanelApiKeysApiMock;
	let notificationsServiceMock: NotificationsServiceMock;

	const API_KEY = {
		created: true
	};
	const TOO_MUCH_KEYS_ALREADY_CREATED_API_KEY_RESPONSE = {
		created: false,
		failReason: 'TOO_MUCH_KEYS_ALREADY_CREATED'
	};
	const UNMAPPED_FAILED_API_KEY_RESPONSE = {
		created: false,
		failReason: 'UNMAPPED_REASON'
	};
	const API_KEY_1 = {
		id: 10
	};
	const API_KEY_2 = {
		id: 20
	};
	const API_KEYS = {
		apiKeys: [API_KEY_1, API_KEY_2]
	};
	const API_KEY_REMOVAL = {
		removed: true
	};
	const API_KEY_SAVE_SUCCESSFUL = {
		successful: true,
		changed: true
	};
	const API_KEY_SAVE_UNCHANGED = {
		successful: true,
		changed: false
	};
	const API_KEY_SAVE_UNSUCCESSFUL = {
		successful: false,
		changed: false,
		failReason: ''
	};

	beforeEach(async(() => {
		panelApiKeysApiMock = new PanelApiKeysApiMock();
		notificationsServiceMock = new NotificationsServiceMock();

		spyOn(panelApiKeysApiMock, 'removeApiKey').and.returnValue(Promise.resolve(API_KEY_REMOVAL));
		spyOn(panelApiKeysApiMock, 'getApiKeys').and.returnValue(Promise.resolve(API_KEYS));
		spyOn(notificationsServiceMock, 'success');
		spyOn(notificationsServiceMock, 'info');
		spyOn(notificationsServiceMock, 'error');

		TestBed.configureTestingModule({
			declarations: [PanelApiKeysComponent],
			schemas: [NO_ERRORS_SCHEMA],
			providers: [
				{
					provide: PanelApiKeysApi,
					useValue: panelApiKeysApiMock
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
		fixture = TestBed.createComponent(PanelApiKeysComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('load API keys on init', () => {
		fixture.whenStable().then(() => {
			expect(component.getApiKeys()).toEqual(API_KEYS.apiKeys);
			expect(component.hasApiKeys()).toBeTrue();
		});
	});

	it('creates API key', () => {
		spyOn(panelApiKeysApiMock, 'createApiKey').and.returnValue(Promise.resolve(API_KEY));

		fixture.whenStable().then(() => {
			component.createApiKey();
			expect(panelApiKeysApiMock.createApiKey).toHaveBeenCalled();
		});
	});

	it('when api key cannot be created, and response is too much keys already created, message is shown', () => {
		spyOn(panelApiKeysApiMock, 'createApiKey').and.returnValue(Promise
			.resolve(TOO_MUCH_KEYS_ALREADY_CREATED_API_KEY_RESPONSE));

		fixture.whenStable().then(() => {
			component.createApiKey();
			expect(panelApiKeysApiMock.createApiKey).toHaveBeenCalled();
			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.error)
					.toHaveBeenCalledWith('You already created the maximal number of API keys.');
			});
		});
	});

	it('when api key cannot be created, and response is too much keys already created, message is shown', () => {
		spyOn(panelApiKeysApiMock, 'createApiKey').and.returnValue(Promise
			.resolve(UNMAPPED_FAILED_API_KEY_RESPONSE));

		fixture.whenStable().then(() => {
			component.createApiKey();
			expect(panelApiKeysApiMock.createApiKey).toHaveBeenCalled();
			fixture.whenStable().then(() => {
				expect(notificationsServiceMock.error)
					.toHaveBeenCalledWith('Uknown error occured. Code: UNMAPPED_REASON');
			});
		});
	});

	it('ask for API key removal, then reverts state', () => {
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			expect(component.removingApiKey(apiKeyId)).toBeFalse();

			component.askForApiKeyRemoval(apiKeyId);
			expect(component.removingApiKey(apiKeyId)).toBeTrue();

			component.doNotRemoveApiKey(apiKeyId);
			expect(component.removingApiKey(apiKeyId)).toBeFalse();
		});
	});

	it('ask for API key removal, then removes key', () => {
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			expect(component.removingApiKey(apiKeyId)).toBeFalse();

			component.askForApiKeyRemoval(apiKeyId);
			expect(component.removingApiKey(apiKeyId)).toBeTrue();

			component.removeApiKey(apiKeyId);
			expect(panelApiKeysApiMock.removeApiKey).toHaveBeenCalled();
		});
	});

	it('ask for API key edit, then reverts state', () => {
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			expect(component.editingApiKey(apiKeyId)).toBeFalse();

			component.openApiKeyEdit(apiKeyId);
			expect(component.editingApiKey(apiKeyId)).toBeTrue();

			component.closeApiKeyEdit(apiKeyId);
			expect(component.editingApiKey(apiKeyId)).toBeFalse();
		});
	});

	it('ask for API key edit, then saves state, then display successful notification', () => {
		spyOn(panelApiKeysApiMock, 'saveApiKeyDetails').and.returnValue(Promise.resolve(API_KEY_SAVE_SUCCESSFUL));
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			expect(component.editingApiKey(apiKeyId)).toBeFalse();

			component.openApiKeyEdit(apiKeyId);
			expect(component.editingApiKey(apiKeyId)).toBeTrue();

			component.updateApiKey(apiKeyId);

			fixture.whenStable().then(() => {
				expect(panelApiKeysApiMock.saveApiKeyDetails).toHaveBeenCalled();
				expect(component.editingApiKey(apiKeyId)).toBeFalse();
				expect(notificationsServiceMock.success).toHaveBeenCalledWith('API key details saved!');
			});
		});
	});

	it('ask for API key edit, then saves state, then display unchanged notification', () => {
		spyOn(panelApiKeysApiMock, 'saveApiKeyDetails').and.returnValue(Promise.resolve(API_KEY_SAVE_UNCHANGED));
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			component.openApiKeyEdit(apiKeyId);
			component.updateApiKey(apiKeyId);

			fixture.whenStable().then(() => {
				expect(panelApiKeysApiMock.saveApiKeyDetails).toHaveBeenCalled();
				expect(notificationsServiceMock.info).toHaveBeenCalledWith('There was no changes to save.');
			});
		});
	});

	it('ask for API key edit, then saves state, then display error notification for URL being too long', () => {
		API_KEY_SAVE_UNSUCCESSFUL.failReason = 'URL_TOO_LONG';
		spyOn(panelApiKeysApiMock, 'saveApiKeyDetails').and.returnValue(Promise.resolve(API_KEY_SAVE_UNSUCCESSFUL));
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			component.openApiKeyEdit(apiKeyId);
			component.updateApiKey(apiKeyId);

			fixture.whenStable().then(() => {
				expect(panelApiKeysApiMock.saveApiKeyDetails).toHaveBeenCalled();
				expect(notificationsServiceMock.error)
					.toHaveBeenCalledWith('URL is too long. Max length is 256 characters.');
			});
		});
	});

	it('ask for API key edit, then saves state, then display error notification for description being too long', () => {
		API_KEY_SAVE_UNSUCCESSFUL.failReason = 'DESCRIPTION_TOO_LONG';
		spyOn(panelApiKeysApiMock, 'saveApiKeyDetails').and.returnValue(Promise.resolve(API_KEY_SAVE_UNSUCCESSFUL));
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			component.openApiKeyEdit(apiKeyId);
			component.updateApiKey(apiKeyId);

			fixture.whenStable().then(() => {
				expect(panelApiKeysApiMock.saveApiKeyDetails).toHaveBeenCalled();
				expect(notificationsServiceMock.error)
					.toHaveBeenCalledWith('Description is too long. Max length is 4000 characters.');
			});
		});
	});

	it('ask for API key edit, then saves state, then display error notification for unknown error', () => {
		API_KEY_SAVE_UNSUCCESSFUL.failReason = 'UNKNOWN_ERROR';
		spyOn(panelApiKeysApiMock, 'saveApiKeyDetails').and.returnValue(Promise.resolve(API_KEY_SAVE_UNSUCCESSFUL));
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			component.openApiKeyEdit(apiKeyId);
			component.updateApiKey(apiKeyId);

			fixture.whenStable().then(() => {
				expect(panelApiKeysApiMock.saveApiKeyDetails).toHaveBeenCalled();
				expect(notificationsServiceMock.error)
					.toHaveBeenCalledWith('Uknown error occured. Code: UNKNOWN_ERROR');
			});
		});
	});
});
