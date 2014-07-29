function GamePresenter() {
  var gSobject = gs.inherit(gs.baseClass,'GamePresenter');
  gSobject.clazz = { name: 'game.GamePresenter', simpleName: 'GamePresenter'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.game = null;
  gSobject.allowMove = true;
  gSobject['init'] = function(it) {
    gSobject.game = Game();
    gs.mc(gs.mc(this,"$",["#result"], gSobject),"html",[""]);
    return gs.mc(gSobject,"drawGame",[]);
  }
  gSobject['drawGame'] = function(it) {
    return gs.mc(gs.mc(this,"$",["#gameTable"], gSobject),"html",[gs.fs('htmlFromGame', this)]);
  }
  gSobject['getHtmlFromGame'] = function(it) {
    return gs.mc(HtmlBuilder,"build",[function(it) {
      return gs.mc(this,"table",[gs.map().add("class","table"), function(it) {
        return gs.mc(gs.gp(gSobject.game,"rows"),"each",[function(row) {
          return gs.mc(this,"tr",[function(it) {
            return gs.mc(row,"each",[function(cell) {
              return gs.mc(this,"td",[gs.map().add("class","cell"), function(it) {
                return gs.mc(this,"p",["" + (gs.elvis(gs.bool(gs.gp(cell,"value")) , gs.gp(cell,"value") , "")) + ""], gSobject);
              }], gSobject);
            }]);
          }], gSobject);
        }]);
      }], gSobject);
    }]);
  }
  gSobject['move'] = function(adress) {
    if (gs.bool(gSobject.allowMove)) {
      gs.mc(gSobject.game,"move" + (gs.mc(adress,"capitalize",[])) + "",[]);
      if (gs.mc(gSobject.game,"isFull",[])) {
        gSobject.allowMove = false;
        gs.mc(gs.mc(this,"$",["#result"], gSobject),"html",["<h2>You have lost</h2>"]);
      } else {
        if (gs.mc(gSobject.game,"isDone",[])) {
          gSobject.allowMove = false;
          gs.mc(gs.mc(this,"$",["#result"], gSobject),"html",["<h2>Congratulations, you have finished!</h2>"]);
        } else {
          gs.mc(gSobject.game,"addRandomNumber",[]);
        };
      };
      return gs.mc(gSobject,"drawGame",[]);
    };
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};

function Game() {
  var gSobject = gs.inherit(gs.baseClass,'Game');
  gSobject.clazz = { name: 'game.Game', simpleName: 'Game'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.size = 4;
  gSobject.total = gs.multiply(gSobject.size, gSobject.size);
  gSobject.goal = 2048;
  gSobject.initialNumbers = 2;
  gSobject.cells = null;
  gSobject.rows = null;
  gSobject.columns = null;
  gSobject['processLine'] = function(line) {
    gs.mc(gSobject,"compressLine",[line]);
    gs.mc(gSobject,"joinLine",[line]);
    return gs.mc(gSobject,"compressLine",[line]);
  }
  gSobject['isDone'] = function(it) {
    return gs.mc(gSobject.cells,"any",[function(it) {
      return gs.gp(it,"value") >= gSobject.goal;
    }]);
  }
  gSobject['isFull'] = function(it) {
    return gs.mc(gSobject.cells,"every",[function(it) {
      return gs.gp(it,"value");
    }]);
  }
  gSobject['addRandomNumber'] = function(it) {
    var empties = gs.mc(gSobject.cells,"findAll",[function(it) {
      return !gs.gp(it,"value");
    }]);
    return gs.sp((empties [ gs.mc(gs.random(),"nextInt",[gs.mc(empties,"size",[])])]),"value",2);
  }
  gSobject['moveRight'] = function(it) {
    return gs.mc(gSobject.rows,"each",[function(it) {
      return gs.mc(gSobject,"processLine",[gs.mc(it,"reverse",[])]);
    }]);
  }
  gSobject['moveLeft'] = function(it) {
    return gs.mc(gSobject.rows,"each",[function(it) {
      return gs.mc(gSobject,"processLine",[it]);
    }]);
  }
  gSobject['moveUp'] = function(it) {
    return gs.mc(gSobject.columns,"each",[function(it) {
      return gs.mc(gSobject,"processLine",[it]);
    }]);
  }
  gSobject['moveDown'] = function(it) {
    return gs.mc(gSobject.columns,"each",[function(it) {
      return gs.mc(gSobject,"processLine",[gs.mc(it,"reverse",[])]);
    }]);
  }
  gSobject['joinLine'] = function(line) {
    return gs.mc(gs.minus(gSobject.size, 1),"times",[function(number) {
      if (gs.equals((line [ number]), (line [ (gs.plus(number, 1))]))) {
        gs.sp((line [ number]),"value",(gs.multiply(gs.gp(line [ number],"value"), 2)));
        return gs.mc(line [ (gs.plus(number, 1))],"reset",[]);
      };
    }]);
  }
  gSobject['compressLine'] = function(line) {
    var lineCompressed = gs.mc(line,"findAll",[function(it) {
      return gs.gp(it,"value");
    }]);
    return gs.mc(line,"eachWithIndex",[function(cell, i) {
      if (i < gs.mc(lineCompressed,"size",[])) {
        return gs.sp(cell,"value",gs.gp(lineCompressed [ i],"value"));
      } else {
        return gs.mc(cell,"reset",[]);
      };
    }]);
  }
  gSobject['Game0'] = function(it) {
    gSobject.cells = gs.list([]);
    gSobject.rows = gs.list([]);
    gSobject.columns = gs.list([]);
    gs.mc(gSobject.total,"times",[function(it) {
      return gs.mc(gSobject.cells,'leftShift', gs.list([Cell()]));
    }]);
    gs.mc(gSobject.size,"times",[function(number) {
      var row = gs.list([]);
      var column = gs.list([]);
      gs.mc(gSobject.size,"times",[function(it) {
        gs.mc(row,'leftShift', gs.list([(gSobject.cells [ (gs.plus((gs.multiply(number, gSobject.size)), it))])]));
        return gs.mc(column,'leftShift', gs.list([(gSobject.cells [ (gs.plus((gs.multiply(it, gSobject.size)), number))])]));
      }]);
      (gSobject.rows [ number]) = row;
      return (gSobject.columns [ number]) = column;
    }]);
    gs.mc(gSobject.initialNumbers,"times",[function(it) {
      return gs.mc(gSobject,"addRandomNumber",[]);
    }]);
    return this;
  }
  if (arguments.length==0) {gSobject.Game0(); }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};

function Cell() {
  var gSobject = gs.inherit(gs.baseClass,'Cell');
  gSobject.clazz = { name: 'game.Cell', simpleName: 'Cell'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.value = null;
  gSobject['reset'] = function(it) {
    return gSobject.value = null;
  }
  gSobject['equals'] = function(other) {
    return ((gs.bool(gs.gp(gs.thisOrObject(this,gSobject),"value"))) && (gs.bool(gs.gp(other,"value")))) && (gs.equals(gSobject.value, gs.gp(other,"value")));
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
