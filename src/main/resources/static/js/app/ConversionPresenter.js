//Grooscript converted file
function ConversionPresenter() {
  var gSobject = gs.inherit(gs.baseClass,'ConversionPresenter');
  gSobject.clazz = { name: 'conversion.ConversionPresenter', simpleName: 'ConversionPresenter'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.gQuery = GQueryImpl();
  gSobject.resultDiv = gs.mc(gSobject,"gQuery",["#results"]);
  gSobject.url = null;
  gSobject.groovyEditor = null;
  gSobject['init'] = function(initUrl, conversionButtonId, clearButtonId) {
    if (initUrl === undefined) initUrl = "";
    gs.mc(gSobject,"initEditor",[]);
    gs.mc(gSobject,"clearResults",[]);
    gSobject.url = initUrl;
    gs.mc(gs.mc(gSobject,"gQuery",[conversionButtonId]),"click",[gSobject["doConversion"]]);
    gs.mc(gs.mc(gSobject,"gQuery",[clearButtonId]),"click",[gSobject["clearEditor"]]);
    return gs.mc(gSobject.groovyEditor,"setValue",["//List\ndef max = [1, 3, 8].collect { it * 2 }.max()\nprintln \"Maximum double is: $max\"\nassert max == 16\n//Traits example\ntrait FlyingAbility {\n  String fly() { \"I'm flying!\" }\n}\nclass Bird implements FlyingAbility {}\ndef b = new Bird()\nprintln b.fly()\nassert b.fly() == \"I'm flying!\""]);
  }
  gSobject['doConversion'] = function(it) {
    gs.mc(gSobject,"clearResults",[]);
    return gs.mc(gSobject.gQuery,"doRemoteCall",[gSobject.url, "POST", gs.map().add("groovyCode",gs.mc(gSobject.groovyEditor,"getValue",[])), function(convertedCode) {
      if (gs.mc(convertedCode,"hasErrors",[])) {
        gs.mc(gSobject,"resultInfo",["errorMessage", gs.elvis(gs.bool(gs.gp(convertedCode,"errorMessage")) , gs.gp(convertedCode,"errorMessage") , gs.elvis(gs.plus("", gs.gp(convertedCode,"console")) , gs.plus("", gs.gp(convertedCode,"console")) , ""))]);
      } else {
        gs.mc(gSobject,"resultInfo",["consoleMessage", gs.elvis(gs.bool(gs.gp(convertedCode,"console")) , gs.gp(convertedCode,"console") , "")]);
      };
      if (gs.bool(gs.gp(convertedCode,"jsCode"))) {
        return gs.mc(gSobject.resultDiv,"append",[gs.plus((gs.plus("<pre>", gs.gp(convertedCode,"jsCode"))), "</pre>")]);
      };
    }, function(error) {
      return gs.mc(gSobject,"resultInfo",["errorMessage", "Error: " + (error) + ""]);
    }, ConvertedCode]);
  }
  gSobject.initEditor = function() {
    this.groovyEditor = ace.edit("editor");
        this.groovyEditor.setTheme("ace/theme/textmate");
        this.groovyEditor.getSession().setMode("ace/mode/groovy");
  }
  gSobject['clearResults'] = function(it) {
    return gs.mc(gSobject.resultDiv,"html",[""]);
  }
  gSobject['clearEditor'] = function(it) {
    gs.mc(gSobject.groovyEditor,"setValue",[""]);
    gs.mc(gSobject,"clearResults",[]);
    return gs.mc(gs.mc(gSobject,"gQuery",[gSobject.groovyEditor]),"focus",[]);
  }
  gSobject['resultInfo'] = function(nameClass, code) {
    return gs.mc(gSobject.resultDiv,"html",[gs.execStatic(HtmlBuilder,'build', this,[function(it) {
      return gs.mc(this,"pre",[gs.map().add("class",nameClass), function(it) {
        return gs.mc(this,"yieldUnescaped",[code], gSobject);
      }], gSobject);
    }])]);
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
