requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

// Start the main app logic.
requirejs(['jquery', 'grooscript', 'JQueryUtils', 'grooscript-binder', 'app/Item'], function($) {
    var jQueryUtils = JQueryUtils();
    item = Item();

    var binder = Binder();
    binder.jQueryUtils = jQueryUtils;
    $(document).ready(function() {
        binder.bindAllProperties(item);
        binder.bindAllMethods(item);
    });

});