requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

requirejs(['jquery', 'grooscript'], function($) {
    //requirejs(['JQueryUtils', 'grooscript-binder', 'grooscript-builder'], function() {
    //});
});
