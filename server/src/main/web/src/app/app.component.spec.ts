import { TestBed, async } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';

import {RouterTestingModule} from "@angular/router/testing";
import {Router} from "@angular/router";

class FeatureSwitchApiMock {
	public isEnabled() {}
}

describe('AppComponent', () => {
	let featureSwitchApiMock: FeatureSwitchApiMock;

	beforeEach(async(() => {
		featureSwitchApiMock = new FeatureSwitchApiMock();

		TestBed.configureTestingModule({
			imports: [RouterTestingModule.withRoutes([])],
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [AppComponent],
			providers: [
				{
					provide: FeatureSwitchApi,
					useValue: featureSwitchApiMock
				}
			]
		}).compileComponents();
	}));

	it('creates the app', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		expect(app).toBeTruthy();
	}));

	it('tells whether panel is enabled', async(() => {
		const fixture = TestBed.createComponent(AppComponent);

		let isEnabled: jasmine.Spy = spyOn(featureSwitchApiMock, 'isEnabled');

		isEnabled.and.callFake((featureSwitchName) => {
			return featureSwitchName === 'ADMIN_PANEL';
		});

		const panelIsEnabled = fixture.componentInstance.panelIsEnabled();

		expect(isEnabled.calls.argsFor(0)[0]).toBe('USER_PANEL');
		expect(isEnabled.calls.argsFor(1)[0]).toBe('ADMIN_PANEL');
		expect(panelIsEnabled).toBeTrue();
	}));
});
