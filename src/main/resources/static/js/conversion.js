require(['./common'], function () {
    require(['jquery', 'grooscript.min', 'grooscript-tools','ace'], function($) {
        require(['app/ConversionPresenter'], function() {
            ConversionPresenter().init('/conversion', '#buttonConvert', '#clearButton');
        });
    });
});
