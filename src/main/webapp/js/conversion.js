require(['./common'], function () {
    require(['jquery', 'ace', 'app/ConvertedCode'], function($) {
        groovyEditor = ace.edit("editor");
        groovyEditor.setTheme("ace/theme/textmate");
        groovyEditor.getSession().setMode("ace/mode/groovy");

        function clearResults() {
            $('#results').html('');
        }

        function setResultInfo(className, message) {
            $('#results').html("<pre class='"+className+"'>"+message+"</pre>");
        }

        $('#buttonConvert').click(function () {
            clearResults();
            $.ajax({
                type: "POST",
                url: "/conversion",
                data: { groovyCode: groovyEditor.getValue() }
            })
            .done(function( msg ) {
                var convertedCode = ConvertedCode();
                gs.passMapToObject(msg, convertedCode);
                if (convertedCode.hasErrors()) {
                    setResultInfo('errorMessage', convertedCode.errorMessage + convertedCode.console);
                } else {
                    setResultInfo('consoleMessage', convertedCode.console);
                };
                $('#results').append('<pre>'+convertedCode.jsCode+'</pre>')
            });
        });
    });
});
