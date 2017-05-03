import React, { Component } from 'react';
import './ApiBrowser.css';
import { SearchStateService } from '../../service/SearchStateService.js';
import { RestApi } from '../../service/rest/RestApi.js';

export class ApiBrowser extends Component {

	constructor() {
		super();
		this.restApi = RestApi.getInstance();
		var self = this;
		SearchStateService.subscribe((state) => {
			self.updateFromSearch(state);
		});
	}

	render() {
		return (
			<div className='api-browser'>
				<div className="api-browser__response">
					{this.renderContent()}
				</div>
			</div>
		);
	}

	updateFromSearch(state) {
		this.restApi.search(state.symbol, state.phrase).then(response => {
			this.response = response;
			this.forceUpdate();
		});
	}

	updateFromEntityPick(uid) {
		this.restApi.get(this.extractSymbol(uid), uid).then(response => {
			this.response = response;
			this.forceUpdate();
		});
	}

	renderContent() {
		var items = [];

		if (!this.response) {
			return items;
		}

		var content = this.response.content;
		var keys = Object.keys(content);

		if (!keys.length) {
			return items;
		}

		var level = 0;

		if (Array.isArray(content)) {
			for (let i = 0; i < content.length; i++) {
				items.push(this.doRender(content[i], level));

				if (i + 1 < content.length && content.length > 1) {
					items.push(<li key={i} className='api-browser__separator'></li>);
				}
			}
		} else {
			items.push(this.doRender(content, level));
		}

		return items;
	}

	doRender(content, level) {
		let items = [];
		for (let i in content) {
			if (Array.isArray(content[i])) {
				var childItems = this.doRender(content[i], level + 1);
				for (let j = 0; j < childItems.length; j++) {
					items.push(childItems[j]);
				}
				continue;
			}

			if (typeof content[i] === 'object') {
				if (content[i] === null) {
					continue;
				}

				var childItems = this.doRender(content[i], level + 1);
				items.push(<li><span key={i} className="api-browser__label">{i}:</span><ul>{childItems}</ul></li>);
			}

			let value = this.renderValue(content[i], i);

			if (value === undefined) {
				continue;
			}

			items.push(<li><span key={i} className="api-browser__label">{i}:</span> {value}</li>);
		}

		return items;
	}

	renderValue(value, key) {
		if (Array.isArray(value) && !value.length || value === null || key === 'uid' || typeof value === 'object') {
			return;
		}

		if (typeof value === 'boolean') {
			return value ? 'yes' : undefined;
		}

		return value;
	}

}
