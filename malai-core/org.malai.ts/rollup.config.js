import resolve from 'rollup-plugin-node-resolve';
import commonjs from 'rollup-plugin-commonjs';
import typescript from 'rollup-plugin-typescript2';

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
        typescript()
],
};