//Grooscript converted file
function ConvertedCode() {
  var gSobject = gs.inherit(gs.baseClass,'ConvertedCode');
  gSobject.clazz = { name: 'conversion.ConvertedCode', simpleName: 'ConvertedCode'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.groovyCode = "";
  gSobject.jsCode = "";
  gSobject.console = "";
  gSobject.errorMessage = "";
  gSobject.assertFails = false;
  gSobject['hasErrors'] = function(it) {
    return ((!gs.bool(gSobject.jsCode)) || (gs.bool(gSobject.errorMessage))) || (gs.bool(gSobject.assertFails));
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
