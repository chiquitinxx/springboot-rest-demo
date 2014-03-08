function Presenter() {
  var gSobject = gs.inherit(gs.baseClass,'Presenter');
  gSobject.clazz = { name: 'framework.presenter.Presenter', simpleName: 'Presenter'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.view = null;
  gSobject.model = null;
  gSobject.frameworks = gs.list([]);
  gSobject.nameFramework = null;
  gSobject.urlFramework = null;
  gSobject.urlImageFramework = null;
  gSobject.updateFrameworksList = function(newFrameworks) {
    gSobject.frameworks = newFrameworks;
    return gs.mc(gSobject.view,"updateFrameworks",gs.list([newFrameworks]));
  };
  gSobject.addNewFrameworkToList = function(framework) {
    gs.mc(gSobject.frameworks,'leftShift', gs.list([framework]));
    return gs.mc(gSobject.view,"updateFrameworks",gs.list([gSobject.frameworks]));
  };
  gSobject['onLoad'] = function(it) {
    gSobject.frameworks = gs.list([]);
    gSobject.nameFramework = "";
    gSobject.urlFramework = "";
    gSobject.urlImageFramework = "";
    return gs.mc(gSobject.model,"loadFrameworks",gs.list([gSobject.updateFrameworksList]));
  }
  gSobject['validateFramework'] = function(it) {
    return (((gs.bool(gSobject.nameFramework)) && (gs.bool(gSobject.urlFramework))) && (gs.mc(gs.list(["http://" , "https://"]),"any",gs.list([function(it) {
      return gs.mc(gSobject.urlFramework,"startsWith",gs.list([it]));
    }])))) && (!gs.mc(gs.list([gSobject.nameFramework , gSobject.urlFramework , gSobject.urlImageFramework]),"any",gs.list([function(it) {
      return (gs.bool(it)) && (gs.mc(it,"indexOf",gs.list(["<"])) >= 0);
    }])));
  }
  gSobject['buttonAddFrameworkClick'] = function(it) {
    gs.mc(gSobject.view,"validationError",gs.list([""]));
    if (gs.mc(gSobject,"validateFramework",gs.list([]))) {
      return gs.mc(gSobject.model,"addFramework",gs.list([gSobject.nameFramework, gSobject.urlFramework, gSobject.urlImageFramework, gSobject.addNewFrameworkToList]));
    } else {
      return gs.mc(gSobject.view,"validationError",gs.list(["No, no, no!"]));
    };
  }
  gSobject.Presenter1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.Presenter1(arguments[0]); }
  
  return gSobject;
};
