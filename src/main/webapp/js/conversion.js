require(['./common'], function () {
    require(['jquery', 'ace', 'app/ConvertedCode', 'app/ConversionPresenter'], function($) {
        ConversionPresenter().init('/conversion', '#buttonConvert', '#clearButton');
    });
});
