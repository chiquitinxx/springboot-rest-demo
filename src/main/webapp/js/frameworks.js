require(['./common'], function () {
    require(['jquery', 'app/Framework',
        'app/FrameworksModel', 'app/FrameworksView', 'app/Presenter',
        'sockjs-0.3.4', 'stomp', 'anijs-min'], function($) {

        presenter = Presenter();
        presenter.start();

        $(document).ready(function() {
            connect();
            Binder().call(presenter);
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
