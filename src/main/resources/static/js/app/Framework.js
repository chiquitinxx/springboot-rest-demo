//Grooscript converted file
function Framework() {
  var gSobject = gs.inherit(gs.baseClass,'Framework');
  gSobject.clazz = { name: 'framework.model.Framework', simpleName: 'Framework'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.id = null;
  gSobject.name = null;
  gSobject.url = null;
  gSobject.urlImage = null;
  gSobject.description = null;
  gSobject['hasImage'] = function(it) {
    return (gs.bool(gSobject.urlImage)) && (gs.mc(gs.list([".GIF" , ".PNG" , ".JPG"]),"any",[function(it) {
      return gs.mc(gs.mc(gSobject.urlImage,"toUpperCase",[]),"endsWith",[it]);
    }]));
  }
  gSobject['equals'] = function(other) {
    return (gs.instanceOf(other, "Framework")) && (gs.equals(gs.gp(other,"name"), gs.gp(gs.thisOrObject(this,gSobject),"name")));
  }
  gSobject['isGithub'] = function(it) {
    return gs.mc(gSobject.url,"contains",["github.com"]);
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
