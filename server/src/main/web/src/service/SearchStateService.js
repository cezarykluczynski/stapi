export class SearchStateService {

	static getState() {
		return this.states[this.states.length -1];
	}

	static push(state) {
		this.states = this.states || [];
		this.states.push(state);
		for (let i = 0; i < this.handlers.length; i++) {
			this.handlers[i](state);
		}
	}

	static subscribe(handler) {
		this.handlers = this.handlers || [];
		this.handlers.push(handler);
	}

}
