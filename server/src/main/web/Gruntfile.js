module.exports = function(grunt) {
	var properties = require('properties-parser');
	var fs = require('fs');
	var obj = {};
	try {
		obj = properties.parse(fs.readFileSync('./../resources/application-stapi-custom.properties', 'utf8'));
	} catch (e) {}

	var termsOfService = '';
	var termsOfServiceTranslation = '';
	var privacyPolicy = '';
	var privacyPolicyTranslation = '';

	try {
		termsOfService = fs.readFileSync(obj['legal.termsOfService']);
	} catch (e) {
		console.warn('Terms of service file not found, continuing with empty string...');
	}
	try {
		termsOfServiceTranslation = fs.readFileSync(obj['legal.termsOfServiceTranslation']);
	} catch (e) {
		console.warn('Terms of service translation file not found, continuing with empty string...');
	}
	try {
		privacyPolicy = fs.readFileSync(obj['legal.privacyPolicy']);
	} catch (e) {
		console.warn('Privacy policy file not found, continuing with empty string...');
	}
	try {
		privacyPolicyTranslation = fs.readFileSync(obj['legal.privacyPolicyTranslation']);
	} catch (e) {
		console.warn('Privacy policy translation file not found, continuing with empty string...');
	}

	function wrapLegal(content, translation) {
		return '<app-root></app-root><script>document.write("<style>.legal-holder,.legal-holder-translation {display: none}</style>");'
				+ '</script><div class="legal-holder">' + content + '</div><div class="legal-holder-translation">' + translation + '</div>';
	}

	grunt.initConfig({
		clean: {
			options: {
				force: true
			},
			build: ['dist', '../resources/build', '../../../build/resources/main', '../../../out/production/resources/build'],
		},
		copy: {
			main: {
				files: [
					{
						expand: true,
						src: '**',
						cwd: 'dist',
						dest: '../resources/build'
					},
					{
						expand: true,
						src: '**',
						cwd: 'dist',
						dest: '../../../build/resources/main/build'
					},
					{
						expand: false,
						src: '../resources/build/index.html',
						dest: '../resources/build/termsOfService.html',
					},
					{
						expand: false,
						src: '../resources/build/index.html',
						dest: '../../../build/resources/main/build/termsOfService.html',
					},
					{
						expand: false,
						src: '../resources/build/index.html',
						dest: '../resources/build/privacyPolicy.html',
					},
					{
						expand: false,
						src: '../resources/build/index.html',
						dest: '../../../build/resources/main/build/privacyPolicy.html',
					}
				],
				options: {
					process: function (content, srcpath, targetPath) {
						if (srcpath == '../resources/build/index.html') {
							if (targetPath.endsWith('termsOfService.html')) {
								return content.replace(new RegExp('<app-root></app-root>', 'g'),
									wrapLegal(termsOfService, termsOfServiceTranslation));
							} else if (targetPath.endsWith('privacyPolicy.html')) {
								return content.replace(new RegExp('<app-root></app-root>', 'g'),
									wrapLegal(privacyPolicy, privacyPolicyTranslation));
							}
						}
						return content;
					},
				},
			},
		}
	});

	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-copy');
};
