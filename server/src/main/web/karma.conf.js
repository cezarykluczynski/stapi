// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
	config.set({
		basePath: '',
		frameworks: ['jasmine', '@angular/cli', 'jasmine-matchers'],
		plugins: [
			'karma-jasmine',
			'karma-chrome-launcher',
			'karma-jasmine-html-reporter',
			'karma-coverage-istanbul-reporter',
			'@angular/cli/plugins/karma',
			'karma-jasmine-matchers'
		],
		client:{
			clearContext: false // leave Jasmine Spec Runner output visible in browser
		},
		coverageIstanbulReporter: {
			reports: [ 'html', 'lcovonly' ],
			fixWebpackSourcePaths: true
		},
		angularCli: {
			environment: 'dev'
		},
		reporters: ['progress', 'kjhtml'],
		port: 9876,
		colors: true,
		logLevel: config.LOG_INFO,
		autoWatch: true,
		browsers: ['Chrome'],
		singleRun: false
	});
};
