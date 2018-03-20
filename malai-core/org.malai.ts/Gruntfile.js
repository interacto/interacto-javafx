module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        ts: {
            default : {
                tsconfig: './tsconfig.json',
                src : ['src-core/**/*.ts', 'src/**/*.ts'],
                passThrough: true
            }
        },
        clean: {
            src: [
                "src-gen/js"
            ],
            // test : [
            //     "target/test"
            // ]
        },
        mochaTest: {
            test: {
                options: {
                    reporter: 'spec',
                },
                src: ['test/**/*.test.js', 'test/*/*.test.js']
            }
        }
    });

    grunt.loadNpmTasks("grunt-ts");
    grunt.registerTask("default", ["ts"]);
    grunt.loadNpmTasks("grunt-contrib-clean");
};
