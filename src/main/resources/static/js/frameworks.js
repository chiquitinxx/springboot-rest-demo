require(['./common'], function () {
    requirejs(['jquery', 'grooscript.min', 'grooscript-tools'], function($) {
        require(['jquery', 'app/Presenter', 'app/Framework', 'app/FrameworksModel',
            'app/FrameworksView', 'anijs-min'], function($) {

            presenter = Presenter();
            presenter.start();

            $(document).ready(function() {
                presenter.onLoad();
            });
        });
    });
});
