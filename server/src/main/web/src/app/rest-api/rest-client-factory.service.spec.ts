import { TestBed, inject } from '@angular/core/testing';

import { RestClientFactoryService } from './rest-client-factory.service';

describe('RestClientFactoryService', () => {
	beforeEach(() => {
		TestBed.configureTestingModule({
			providers: [RestClientFactoryService]
		});
	});

	it('should be created', inject([RestClientFactoryService], (service: RestClientFactoryService) => {
		expect(service).toBeTruthy();
	}));
});
