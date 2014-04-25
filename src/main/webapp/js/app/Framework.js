//Grooscript converted file
function Framework() {
  var gSobject = gs.inherit(gs.baseClass,'Framework');
  gSobject.clazz = { name: 'framework.model.Framework', simpleName: 'Framework'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.name = null;
  gSobject.url = null;
  gSobject.urlImage = null;
  gSobject['hasImage'] = function(it) {
    return (gs.bool(gSobject.urlImage)) && (gs.mc(gs.list([".GIF" , ".PNG" , ".JPG"]),"any",[function(it) {
      return gs.mc(gs.mc(gSobject.urlImage,"toUpperCase",[]),"endsWith",[it]);
    }]));
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
