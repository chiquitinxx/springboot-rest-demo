require(['./common'], function () {
    require(['gQueryImpl', 'grooscript-binder', 'grooscript-builder'], function() {
        require(['jquery', 'app/Framework',
            'app/FrameworksModel', 'app/FrameworksView', 'app/Presenter'], function($) {

            presenter = Presenter();
            presenter.model = FrameworksModel();
            presenter.view = FrameworksView();

            var binder = Binder({gQuery: GQueryImpl()});
            $(document).ready(function() {
                binder.call(presenter);
                presenter.onLoad();
            });
        });
    });
});
