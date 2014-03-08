requirejs.config({
    baseUrl: 'js/lib',
    paths: {
      app: '../app',
      jquery: 'jquery'
    }
});

// Start the main app logic.
requirejs(['jquery', 'grooscript', 'JQueryUtils', 'grooscript-binder', 'grooscript-builder',
    'app/Framework', 'app/FrameworksModel', 'app/FrameworksView', 'app/Presenter'], function($) {
    var jQueryUtils = JQueryUtils();

    presenter = Presenter();
    presenter.model = FrameworksModel();
    presenter.view = FrameworksView();

    var binder = Binder();
    binder.jQueryUtils = jQueryUtils;
    $(document).ready(function() {
        binder.bindAllProperties(presenter);
        binder.bindAllMethods(presenter);
    });

    presenter.onLoad();

});