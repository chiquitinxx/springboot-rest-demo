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
  gSobject.ConvertedCode1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.ConvertedCode1(arguments[0]); }
  
  return gSobject;
};
