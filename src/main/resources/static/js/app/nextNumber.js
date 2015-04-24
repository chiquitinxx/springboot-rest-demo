//Grooscript converted file
var nextNumber = function(number) {
  return gs.mc(gs.mc(this,"$",["body"]),"html",[gs.execStatic(Templates,'applyTemplate', this,["counterBody.tpl", gs.map().add("number",gs.plus(number, 1))])]);
};
