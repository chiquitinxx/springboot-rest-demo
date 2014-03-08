requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

requirejs(['jquery', 'grooscript', 'JQueryUtils', 'grooscript-binder', 'grooscript-builder'], function($) {
    return $;
});
