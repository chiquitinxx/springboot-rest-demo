requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

requirejs(['jquery', 'grooscript-all'], function($) {

});
