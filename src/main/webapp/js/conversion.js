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

        $(document).ready(function() {
            groovyEditor.setValue('//List\ndef max = [1, 3, 8].collect { it * 2 }.max()\nprintln "Maximum double is: $max"\nassert max == 16\n\n//Traits example\ntrait FlyingAbility {\n  String fly() { "I\'m flying!" }\n}\n\nclass Bird implements FlyingAbility {}\ndef b = new Bird()\nprintln b.fly()\nassert b.fly() == "I\'m flying!"');
        });
    });
});
