require(['./common'], function () {
    require(['jquery', 'app/Presenter', 'app/Framework', 'app/FrameworksModel',
        'app/FrameworksView', 'anijs-min'], function($) {

        presenter = Presenter();
        presenter.start();

        $(document).ready(function() {
            presenter.onLoad();
        });
    });
});
