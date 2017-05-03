import React, { Component } from 'react';
import './components/App.css';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import { Home } from './components/home/Home.js';
import { ApiBrowser } from './components/apiBrowser/ApiBrowser.js';
import { Documentation } from './components/documentation/Documentation.js';
import { Statistics } from './components/statistics/Statistics.js';
import { InitializationService } from './service/InitializationService.js';
import R from 'ramda';

class App extends Component {

	constructor() {
		super();
		this.initializationService = new InitializationService();
		this.restApi = this.initializationService.getRestApi();
		var self = this;
		this.restApi.whenReady(() => {
			self.setState({
				urls: self.restApi.getUrls()
			});
		})
		this.search = this.search.bind(this);
	}

	render() {
		return (
			<div className='App'>
				<Router>
					<div>
						<div className='navbar navbar-default navbar-fixed-top'>
							<div className='container-fluid'>
								<div className='collapse navbar-collapse'>
										<ul className='nav navbar-nav'>
											<li><Link to='/'>Home</Link></li>
											<li><Link to='/api-browser'>API Browser</Link></li>
											<li><Link to='/documentation'>Documentation</Link></li>
											<li><Link to='/statistics'>Statistics</Link></li>
										</ul>
										<form className="navbar-form navbar-left" role="search" onSubmit={this.search}>
											<div className="form-group">
												<input type="text" className="form-control" placeholder="Search the API" />
												<select  className="form-control navigation__search__entity">
													{this.createOptions()}
												</select>
											</div>
											<button type="submit" className="btn btn-default">Submit</button>
										</form>
										<ul className='nav navbar-nav navbar-right'>
											<li className='navigation__github-star'>
												<a className='github-button' href='https://github.com/cezarykluczynski/stapi' data-icon='octicon-star'
													data-style='mega' data-show-count='true'
													aria-label='Star cezarykluczynski/stapi on GitHub'>Star</a>
											</li>
											<li><a href='https://github.com/cezarykluczynski/stapi'>STAPI on GitHub</a></li>
										</ul>
								</div>
							</div>
						</div>
						<div className='container content'>
							<Route exact path='/' component={Home}/>
							<Route exact path='/api-browser' component={ApiBrowser}/>
							<Route exact path='/documentation' component={Documentation}/>
							<Route exact path='/statistics' component={Statistics}/>
						</div>
					</div>
				</Router>
			</div>
		);
	}

	createOptions() {
		let items = [];
		if (!this.state || !this.state.urls) {
			return items;
		}
		for (let i = 0; i < this.state.urls.length; i++) {
			let url = this.state.urls[i];
			items.push(<option key={url.symbol} value={url.symbol}>{url.name}</option>);
		}
		return items;
	}

	search(event) {
		event.preventDefault();
		if (!R.test(/api-browser/, location.href)) {
			history.pushState({}, '', '/api-browser');
		}
	}

}

export default App;
