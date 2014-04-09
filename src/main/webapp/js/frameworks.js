require(['./common'], function () {
    require(['JQueryUtils', 'grooscript-binder', 'grooscript-builder'], function() {
        require(['jquery', 'app/Framework',
            'app/FrameworksModel', 'app/FrameworksView', 'app/Presenter'], function($) {

            presenter = Presenter();
            presenter.model = FrameworksModel();
            presenter.view = FrameworksView();

            var binder = Binder({jQueryUtils: JQueryUtils()});
            $(document).ready(function() {
                binder.bindAllProperties(presenter);
                binder.bindAllMethods(presenter);
                presenter.onLoad();
            });
        });
    });
});
