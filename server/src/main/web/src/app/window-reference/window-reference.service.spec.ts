import { TestBed, inject } from '@angular/core/testing';

import { WindowReferenceService } from './window-reference.service';

describe('WindowReferenceService', () => {
	beforeEach(() => {
		TestBed.configureTestingModule({
			providers: [WindowReferenceService]
		});
	});

	it('should be created', inject([WindowReferenceService], (service: WindowReferenceService) => {
		expect(service).toBeTruthy();
	}));

	it('returns handle to window', inject([WindowReferenceService], (service: WindowReferenceService) => {
		expect(service.getWindow()).toBe(window);
	}));
});
