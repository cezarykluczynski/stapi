export class SearchStateService {

	static getState() {
		return this.states && this.states.length ? this.states[this.states.length -1] : null;
	}

	static push(state, error) {
		this.states = this.states || [];
		this.handlers = this.handlers || [];
		this.states.push(state);
		for (let i = 0; i < this.handlers.length; i++) {
			this.handlers[i](state, error);
		}
	}

	static subscribe(handler) {
		this.handlers = this.handlers || [];
		this.handlers.push(handler);
		const state = this.getState();
		if (state) {
			handler(state, this.error);
		}
	}

	static markInvalid(error) {
		this.invalid = true;
		this.push(JSON.parse(error.response), true);
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
