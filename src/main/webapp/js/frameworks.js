require(['./common'], function () {
    require(['gQueryImpl', 'grooscript-binder', 'grooscript-builder'], function() {
        require(['jquery', 'app/Framework',
            'app/FrameworksModel', 'app/FrameworksView', 'app/Presenter',
            'sockjs-0.3.4', 'stomp'], function($) {

            presenter = Presenter();
            presenter.model = FrameworksModel();
            presenter.view = FrameworksView();

            var binder = Binder({gQuery: GQueryImpl()});
            $(document).ready(function() {
                connect();
                binder.call(presenter);
                presenter.onLoad();
            });

            function connect() {
                var socket = new SockJS('/notify');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function(frame) {
                    stompClient.subscribe('/topic/newFramework', function(msg){
                        presenter.addNewFrameworkToList(gs.toGroovy(JSON.parse(msg.body), Framework))
                    });
                });
            }
        });
    });
});
