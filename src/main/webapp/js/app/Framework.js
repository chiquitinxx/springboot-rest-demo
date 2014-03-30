function Framework() {
  var gSobject = gs.inherit(gs.baseClass,'Framework');
  gSobject.clazz = { name: 'framework.model.Framework', simpleName: 'Framework'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.name = null;
  gSobject.url = null;
  gSobject.urlImage = null;
  gSobject['hasImage'] = function(it) {
    return (gs.bool(gSobject.urlImage)) && (gs.mc(gs.list([".GIF" , ".PNG" , ".JPG"]),"any",gs.list([function(it) {
      return gs.mc(gs.mc(gSobject.urlImage,"toUpperCase",gs.list([])),"endsWith",gs.list([it]));
    }])));
  }
  gSobject.Framework1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.Framework1(arguments[0]); }
  
  return gSobject;
};
