package framework.model

import org.grooscript.asts.GsNative

/**
 * Created by jorge on 01/03/14.
 */
class FrameworksModel {

    @GsNative
    def loadFrameworks(Closure onLoaded) {/*
        $.ajax({
          type: "GET",
          url: "/frameworks"
        })
        .done(function( msg ) {
            var list = gs.list(msg);
            onLoaded(list);
        });
    */}

    @GsNative
    def addFramework(String name, String url, String urlImage, Closure onAdded) {/*
        console.log('onAdded before:'+onAdded);
            $.ajax({
            type: "POST",
            url: "/frameworks",
            data: { name: name, url: url, urlImage: urlImage }
        })
        .done(function( msg ) {
            console.log('onAdded later:'+onAdded);
            var framework = Framework();
            gs.passMapToObject(msg, framework);
            onAdded(framework);
        });
    */}
}
