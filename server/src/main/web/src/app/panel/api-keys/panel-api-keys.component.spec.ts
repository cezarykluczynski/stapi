import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelApiKeysComponent } from './panel-api-keys.component';
import { PanelApiKeysApi } from './panel-api-keys-api.service';

class PanelApiKeysApiMock {
	public createApiKey() {}
	public removeApiKey() {}
	public getApiKeys() {}
}

describe('PanelApiKeysComponent', () => {
	let component: PanelApiKeysComponent;
	let fixture: ComponentFixture<PanelApiKeysComponent>;
	let panelApiKeysApiMock: PanelApiKeysApiMock;

	const API_KEY = {
		created: true
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

	beforeEach(async(() => {
		panelApiKeysApiMock = new PanelApiKeysApiMock();
		spyOn(panelApiKeysApiMock, 'createApiKey').and.returnValue(Promise.resolve(API_KEY));
		spyOn(panelApiKeysApiMock, 'removeApiKey').and.returnValue(Promise.resolve(API_KEY_REMOVAL));
		spyOn(panelApiKeysApiMock, 'getApiKeys').and.returnValue(Promise.resolve(API_KEYS));

		TestBed.configureTestingModule({
			declarations: [PanelApiKeysComponent],
			providers: [
				{
					provide: PanelApiKeysApi,
					useValue: panelApiKeysApiMock
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
		fixture.whenStable().then(() => {
			component.createApiKey();
			expect(panelApiKeysApiMock.createApiKey).toHaveBeenCalled();
		});
	});

	it('ask for API key removal, then reverts state', () => {
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			component.askForApiKeyRemoval(apiKeyId);
			expect(component.removingApiKey(apiKeyId)).toBeTrue();

			component.doNotRemoveApiKey(apiKeyId);
			expect(component.removingApiKey(apiKeyId)).toBeFalse();
		});
	});

	it('ask for API key removal, then removed key', () => {
		fixture.whenStable().then(() => {
			const apiKeyId = API_KEYS.apiKeys[0].id;
			component.askForApiKeyRemoval(apiKeyId);
			expect(component.removingApiKey(apiKeyId)).toBeTrue();

			component.removeApiKey(apiKeyId);
			expect(panelApiKeysApiMock.removeApiKey).toHaveBeenCalled();
		});
	});
});
