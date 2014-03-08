function Framework() {
  var gSobject = gs.inherit(gs.baseClass,'Framework');
  gSobject.clazz = { name: 'framework.model.Framework', simpleName: 'Framework'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.name = null;
  gSobject.url = null;
  gSobject.urlImage = null;
  gSobject.Framework1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.Framework1(arguments[0]); }
  
  return gSobject;
};
