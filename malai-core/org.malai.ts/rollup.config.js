import resolve from 'rollup-plugin-node-resolve';
import commonjs from 'rollup-plugin-commonjs';
import typescript from 'rollup-plugin-typescript2';
import includePaths from 'rollup-plugin-includepaths';

let includePathOptions = {
  include: {},
    paths : ['src/*'],
    external: [],
    extensions: ['.ts', '.js']
};

export default {
    input: './src/index.ts',
    output: {
        name: 'WidgetBinder',
        file : 'dist/widgetbinder.js',
        format: 'umd'
    },
    plugins: [
        resolve(),
        commonjs(),
        typescript(),
        includePaths(includePathOptions)
],
};