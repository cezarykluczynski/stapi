import { Injectable } from '@angular/core';

function _window() : any {
	return window;
}

@Injectable()
export class WindowReferenceService {

	constructor() { }

	getWindow(): any {
		return _window();
	}

}
