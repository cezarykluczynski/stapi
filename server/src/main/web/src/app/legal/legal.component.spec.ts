import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LegalComponent} from './legal.component';
import {FeatureSwitchApi} from '../feature-switch/feature-switch-api.service';
import {Router} from '@angular/router';

class FeatureSwitchApiMock {
	public isEnabled() {}
}

class RouterMock {
	public navigate() {}
}

describe('LegalComponent', () => {
	let component: LegalComponent;
	let routerMock: RouterMock;
	let featureSwitchApiMock: FeatureSwitchApiMock;
	let fixture: ComponentFixture<LegalComponent>;

	beforeEach(async(() => {
		featureSwitchApiMock = new FeatureSwitchApiMock();
		routerMock = new RouterMock();

		TestBed.configureTestingModule({
			declarations: [LegalComponent],
			providers: [
				{
					provide: FeatureSwitchApi,
					useValue: featureSwitchApiMock
				},
				{
					provide: Router,
					useValue: routerMock
				},
			]
		})
		.compileComponents();
	}));

	it('creates', () => {
		fixture = TestBed.createComponent(LegalComponent);
		component = fixture.componentInstance;
		expect(component).toBeTruthy();
	});

	describe('when there is TOS and PP enabled', () => {
		beforeEach(() => {
			spyOn(featureSwitchApiMock, 'isEnabled').and.returnValue(true);
			fixture = TestBed.createComponent(LegalComponent);
			component = fixture.componentInstance;
			fixture.detectChanges();
		});

		it('translation can be toggled on and off', () => {
			expect(component.showTranslation()).toBeFalse();

			component.toggleTranslation();

			expect(component.showTranslation()).toBeTrue();

			component.toggleTranslation();

			expect(component.showTranslation()).toBeFalse();
		});
	});

	describe('when there is no TOS and PP enabled', () => {
		beforeEach(() => {
			spyOn(featureSwitchApiMock, 'isEnabled').and.returnValue(false);
			spyOn(routerMock, 'navigate');
			fixture = TestBed.createComponent(LegalComponent);
			component = fixture.componentInstance;
			fixture.detectChanges();
		});

		it('redirects to main page', () => {
			fixture.whenStable().then(() => {
				expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
			});
		});
	});
});
