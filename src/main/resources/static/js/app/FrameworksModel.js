//Grooscript converted file
function FrameworksModel() {
  var gSobject = gs.inherit(gs.baseClass,'FrameworksModel');
  gSobject.clazz = { name: 'framework.model.FrameworksModel', simpleName: 'FrameworksModel'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.gQuery = GQueryImpl();
  gSobject['loadFrameworks'] = function(onLoaded) {
    return gs.mc(gSobject.gQuery,"doRemoteCall",["/frameworks", "GET", null, onLoaded, null, Framework]);
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
