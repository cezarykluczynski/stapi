import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelAdminAccountsComponent } from './panel-admin-accounts.component';

describe('PanelAdminAccountsComponent', () => {
	let component: PanelAdminAccountsComponent;
	let fixture: ComponentFixture<PanelAdminAccountsComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ PanelAdminAccountsComponent ]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PanelAdminAccountsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
