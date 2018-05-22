import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LegalComponent } from './legal.component';

describe('LegalComponent', () => {
	let component: LegalComponent;
	let fixture: ComponentFixture<LegalComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
		  declarations: [LegalComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(LegalComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});

	it('translation can be toggled on and off', () => {
		expect(component.showTranslation()).toBeFalse();

		component.toggleTranslation();

		expect(component.showTranslation()).toBeTrue();

		component.toggleTranslation();

		expect(component.showTranslation()).toBeFalse();
	});
});
