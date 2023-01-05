import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { LegalComponent } from './legal.component';
import { FeatureSwitchApi } from '../feature-switch/feature-switch-api.service';
import { Router } from '@angular/router';

class FeatureSwitchApiMock {
	public isEnabled(): any {}
}

describe('LegalComponent', () => {
	let component: LegalComponent;
	let routerMock: jasmine.SpyObj<Router>;
	let featureSwitchApiMock: FeatureSwitchApiMock;
	let fixture: ComponentFixture<LegalComponent>;

	beforeEach(waitForAsync(() => {
		featureSwitchApiMock = new FeatureSwitchApiMock();
		routerMock = jasmine.createSpyObj('Router', ['navigate'], ['']);

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
			fixture = TestBed.createComponent(LegalComponent);
			component = fixture.componentInstance;
			fixture.detectChanges();
		});

		it('redirects to main page', waitForAsync(() => {
			fixture.whenStable().then(() => {
				expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
			});
		}));
	});
});
