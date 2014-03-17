require(['./common'], function () {
    require(['JQueryUtils', 'grooscript-binder', 'grooscript-builder'], function() {
        require(['jquery', 'app/Framework', 'app/FrameworksModel', 'app/FrameworksView', 'app/Presenter'], function($) {
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
    });
});
