export class SearchStateService {

	static getState() {
		return this.states && this.states.length ? this.states[this.states.length -1] : null;
	}

	static push(state) {
		this.error = false;
		this.states = this.states || [];
		this.handlers = this.handlers || [];
		this.states.push(state);
		for (let i = 0; i < this.handlers.length; i++) {
			this.handlers[i](state);
		}
	}

	static subscribe(handler) {
		this.handlers = this.handlers || [];
		this.handlers.push(handler);
		const state = this.getState();
		if (state) {
			handler(state);
		}
	}

	static markInvalid(error) {
		this.invalid = true;
		this.error = error;
	}

	static getError() {
		return this.error;
	}

	static markValid() {
		this.invalid = false;
	}

	static isInvalid() {
		return typeof this.invalid === 'undefined' || this.invalid;
	}

}
