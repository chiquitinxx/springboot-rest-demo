//Grooscript converted file
function Presenter() {
  var gSobject = gs.inherit(gs.baseClass,'Presenter');
  gSobject.clazz = { name: 'framework.presenter.Presenter', simpleName: 'Presenter'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.view = null;
  gSobject.model = null;
  gSobject.frameworks = gs.list([]);
  gSobject.updateFrameworksList = function(newFrameworks) {
    gSobject.frameworks = newFrameworks;
    return gs.mc(gSobject.view,"updateFrameworks",[newFrameworks]);
  };
  gSobject['onLoad'] = function(it) {
    gSobject.frameworks = gs.list([]);
    return gs.mc(gSobject.model,"loadFrameworks",[gSobject.updateFrameworksList]);
  }
  gSobject['start'] = function(it) {
    gSobject.view = FrameworksView();
    return gSobject.model = FrameworksModel();
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
