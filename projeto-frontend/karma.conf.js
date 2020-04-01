// Karma configuration
// Generated on Mon Nov 28 2016 16:31:49 GMT-0200 (BRST)

module.exports = function (config) {
    config.set({
        // hostname : 'localhost.bb.com.br',

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '.',

        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            // Para permitir a execução dos testes em ambiente de integração contínua, é necessário incluir os as bibliotecas JavaScript
            // necessárias para a execução da aplicação neste ponto
            { pattern: 'node_modules/angular/angular.js', watched: false},
            { pattern: 'node_modules/angular-mocks/angular-mocks.js', watched: false},
            { pattern: 'https://desenv.estatico.bb.com.br/gaw-commons/libs/angular-sanitize/1.5.9/angular-sanitize.min.js', watched: false},
            'src/app/stefanini/*.js',
            'src/**/*.module.js',
            'src/**/*.route.js',
            'src/**/*.utils.js',
            'src/**/*.service.js',
            'src/**/*.controller.js',
            'src/**/*.html',
            'specs/**/*-spec.js'
        ],

        // list of files to exclude
        exclude: [''],

                // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
            'src/**/*.js': ['coverage'],
            'src/**/templates-diretivas/*.tpl.html': ['ng-html2js'],
          },

        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress', 'coverage'],

        coverageReporter: {
            dir: 'coverage/javascript',
            reporters: [{
              type: 'lcov',
              subdir: 'lcov'
            }]
        },

        // web server port
        port: 9876,

        // enable / disable colors in the output (reporters and logs)
        colors: true,

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_LOG,

        // enable / disable watching file and executing tests whenever any file changes
        // autoWatch: true,

        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        // browsers: ['chrome_without_security'],
        // browsers: ['PhantomJS'],
        // browsers: ['chrome_without_security'],
	     browsers: ['phanthomjs_without_security'],
        
customLaunchers: {
            chrome_without_security: {
                base: 'Chrome',
                flags: ['--args', '--ignore-certificate-errors']
            },
            phanthomjs_without_security: {
                base: 'PhantomJS',
                flags: ['--ignore-ssl-errors=true', '--web-security=false']
            }
        },

        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false,

        // Concurrency level
        // how many browser should be started simultaneous
        concurrency: Infinity

        // client: {
        //     captureConsole: true
        // }
    });
};
