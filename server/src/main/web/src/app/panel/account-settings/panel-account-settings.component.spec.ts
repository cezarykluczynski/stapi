import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelAccountSettingsComponent } from './panel-account-settings.component';

describe('PanelAccountSettingsComponent', () => {
	let component: PanelAccountSettingsComponent;
	let fixture: ComponentFixture<PanelAccountSettingsComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [PanelAccountSettingsComponent]
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
});
