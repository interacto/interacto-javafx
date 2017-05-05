module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
	typescript_project: {
		options: {
            tsconfig: "./tsconfig.json"
		},
		your_target: {
		  // ALWAYS use files, instead of src/dest 
		  // Note that for EACH dest, will be created a separated TSC project json file, keep that in mind 
		  files: {
		    // its a folder == outDir 
		    'src-gen/js': ['src/**/*.ts'],
		    // its a file == outFile 
		//    'dest/has/extension.js': ['srcs/**/*.ts']
		  }
		}
	  }
  });

  grunt.loadNpmTasks('grunt-typescript-project');
};
