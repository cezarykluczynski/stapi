import React, { Component } from 'react';
import './Info.css';

export class Info extends Component {

	render() {
		return (
			<div className='alert alert-dismissible alert-success' dangerouslySetInnerHTML={{__html: this.props.message}}></div>
		);
	}

}
