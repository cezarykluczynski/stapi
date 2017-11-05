import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelAdminManagementComponent } from './panel-admin-management.component';

describe('PanelAdminManagementComponent', () => {
	let component: PanelAdminManagementComponent;
	let fixture: ComponentFixture<PanelAdminManagementComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [PanelAdminManagementComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PanelAdminManagementComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
