#include (macros.inc)
[top]
components : LUG

[LUG]
type : cell
dim : (100, 100, 2)
delay : transport
defaultDelayTime : 100
border : wrapped 
neighbors : LUG(-1,-1,0) LUG(-1,0,0) LUG(-1,1,0) 
neighbors : LUG(0,-1,0)  LUG(0,0,0) LUG(0,0,1) LUG(0,1,0)
neighbors : LUG(1,-1,0)  LUG(1,0,0)  LUG(1,1,0)
initialvalue : 0
initialcellsvalue : map.val
localtransition : LUG-rule

[LUG-rule]
rule : {(0,0,0) + 2} 100 { #macro(NonUrban) and #macro(Logistic) }
rule : {(0,0,0) - 1} 100 { #macro(Growing) }
rule : {(0,0,0) + 1} 100 { #macro(Urban) and (#macro(UrbanNeighborCount) < 9) }
rule : {(0,0,0)} 100 { t }