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
        tslint: {
            options: {
                configuration: "tslint.json",
                force: false,
                fix: false
            },
            files: {
                src: [
                    "src/**/*.ts",
                    "src-core/**/*.ts",
                ]
            },
            your_target: {
                // Target-specific file lists and/or options go here.
            },
        }
    });

    grunt.loadNpmTasks("grunt-ts");
    grunt.registerTask("default", ["clean", "ts", "tslint"]);
    grunt.loadNpmTasks("grunt-contrib-clean");
    grunt.loadNpmTasks("grunt-tslint");
};
