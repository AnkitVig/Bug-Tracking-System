[top]
components : RiverFlood Rain@Generator
link : out@Rain inputRain@RiverFlood
[RiverFlood]
type : cell
dim : (20,20)
delay : transport
defaultDelayTime  : 1000
border : nowrapped 
neighbors :                   RiverFlood(-1,0)  
neighbors : RiverFlood(0,-1)  RiverFlood(0,0)  RiverFlood(0,1)
neighbors :                   RiverFlood(1,0)  
initialvalue : 0
initialCellsValue : RiverFlood.val
in : inputRain
link : inputRain in@RiverFlood(0,0)
link : inputRain in@RiverFlood(0,19)
link : inputRain in@RiverFlood(19,0)
link : inputRain in@RiverFlood(19,19)
link : inputRain in@RiverFlood(5,5)
link : inputRain in@RiverFlood(5,10)
link : inputRain in@RiverFlood(10,15)
link : inputRain in@RiverFlood(15,10)
link : inputRain in@RiverFlood(15,15)
link : inputRain in@RiverFlood(7,5)
link : inputRain in@RiverFlood(7,6)
link : inputRain in@RiverFlood(7,7)
link : inputRain in@RiverFlood(7,8)
link : inputRain in@RiverFlood(7,9)
link : inputRain in@RiverFlood(8,10)
link : inputRain in@RiverFlood(8,5)
link : inputRain in@RiverFlood(8,6)
link : inputRain in@RiverFlood(8,7)
link : inputRain in@RiverFlood(8,8)
link : inputRain in@RiverFlood(8,9)
link : inputRain in@RiverFlood(8,10)
link : inputRain in@RiverFlood(9,5)
link : inputRain in@RiverFlood(9,6)
link : inputRain in@RiverFlood(9,7)
link : inputRain in@RiverFlood(9,8)
link : inputRain in@RiverFlood(9,9)
link : inputRain in@RiverFlood(9,10)

localtransition : Flood
portInTransition : in@RiverFlood(5,5)  setRain
portInTransition : in@RiverFlood(5,10)  setRain
portInTransition : in@RiverFlood(10,15)  setRain
portInTransition : in@RiverFlood(15,10)  setRain
portInTransition : in@RiverFlood(15,15)  setRain
portInTransition : in@RiverFlood(0,0)  setRain
portInTransition : in@RiverFlood(19,0)  setRain
portInTransition : in@RiverFlood(0,19)  setRain
portInTransition : in@RiverFlood(19,19)  setRain
portInTransition : in@RiverFlood(7,5)  setRain
portInTransition : in@RiverFlood(7,6)  setRain
portInTransition : in@RiverFlood(7,7)  setRain
portInTransition : in@RiverFlood(7,8)  setRain
portInTransition : in@RiverFlood(7,9)  setRain
portInTransition : in@RiverFlood(8,10)  setRain
portInTransition : in@RiverFlood(8,5)  setRain
portInTransition : in@RiverFlood(8,6)  setRain
portInTransition : in@RiverFlood(8,7)  setRain
portInTransition : in@RiverFlood(8,8)  setRain
portInTransition : in@RiverFlood(8,9)  setRain
portInTransition : in@RiverFlood(8,10)  setRain
portInTransition : in@RiverFlood(9,5)  setRain
portInTransition : in@RiverFlood(9,6)  setRain
portInTransition : in@RiverFlood(9,7)  setRain
portInTransition : in@RiverFlood(9,8)  setRain
portInTransition : in@RiverFlood(9,9)  setRain
portInTransition : in@RiverFlood(9,10)  setRain

[Flood]
%water to neighbouring cells when only one cell has water more than average
rule : { (-1,0) + ((0,0)/2) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/2) <= (0,0))) }
rule : { (0,1) + ((0,0)/2) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/2) <= (0,0))) }
rule : { (1,0) + ((0,0)/2) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/2) <= (0,0))) }
rule : { (0,-1) + ((0,0)/2) } 1000 { ((0,0) >= 0) and (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/2) <= (0,0))) }
%water to neighbouring cells when only two cells have water more than average                                                                                                                                                                                       
rule : { (-1,0) + ((0,0)/3) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0))) }
rule : { (0,1) + ((0,0)/3) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/3) <= (0,0))) }
rule : { (-1,0) + ((0,0)/3) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0))) }
rule : { (1,0) + ((0,0)/3) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/3) <= (0,0))) }
rule : { (-1,0) + ((0,0)/3) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0))) }
rule : { (0,-1) + ((0,0)/3) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/3) <= (0,0))) }
rule : { (0,1) + ((0,0)/3) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/3) <= (0,0))) }
rule : { (1,0) + ((0,0)/3) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/3) <= (0,0))) }
rule : { (0,1) + ((0,0)/3) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/3) <= (0,0))) }
rule : { (0,-1) + ((0,0)/3) } 1000 { ((0,0) >= 0) and (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0))) }
rule : { (1,0) + ((0,0)/3) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/3) <= (0,0))) }
rule : { (0,-1) + ((0,0)/3) } 1000 { ((0,0) >= 0) and (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0))) }
%water to neighbouring cells when only three cells have water more than average                                                                                                                                                                                     
rule : { (-1,0) + ((0,0)/4) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/4) <= (0,0))) }
rule : { (0,1) + ((0,0)/4) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/4) <= (0,0))) }
rule : { (1,0) + ((0,0)/4) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/4) <= (0,0))) }
rule : { (-1,0) + ((0,0)/4) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/4) <= (0,0))) }
rule : { (0,1) + ((0,0)/4) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/4) <= (0,0))) }
rule : { (0,-1) + ((0,0)/4) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/4) <= (0,0))) }
rule : { (-1,0) + ((0,0)/4) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/4) <= (0,0))) }
rule : { (1,0) + ((0,0)/4) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/4) <= (0,0))) }
rule : { (0,-1) + ((0,0)/4) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/4) <= (0,0))) }
rule : { (0,1) + ((0,0)/4) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/4) <= (0,0))) }
rule : { (1,0) + ((0,0)/4) } 1000 { ((0,0) >= 0) and  (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/4) <= (0,0))) }
rule : { (0,-1) + ((0,0)/4) } 1000 { ((0,0) >= 0) and (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/4) <= (0,0))) }
%water to neighbouring cells when all four cells have water more than average
rule : { (-1,0) + 4*((0,0)/5) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/5) <= (0,0))) }
rule : { (0,1) + 4*((0,0)/5) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/5) <= (0,0))) }
rule : { (1,0) + 4*((0,0)/5) } 1000 { ((0,0) >= 0) and  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/5) <= (0,0))) }
rule : { (0,-1) + 4*((0,0)/5) } 1000 { ((0,0) >= 0) and (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/5) <= (0,0))) }
%water from center cell when only one cell has water more than average
rule : { (0,0) - ((0,0)/2) } 1000 { ((0,0) >= 0) and 
(  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/2) <= (0,0))) 
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/2) <= (0,0)))
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/2) <= (0,0))) 
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,-1) + (0,0)/2) <= (0,0)))
) }                                                                                                                                                                                                              
%water from center cell when only two cells have water more than average                                                                                                                                          
rule : { (0,0) - 2*((0,0)/3) } 1000 { ((0,0) >= 0) and                                                                                                                                                            
(  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0)) and (((0,1) + (0,0)/3) <= (0,0)))
or (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0)) and (((1,0) + (0,0)/3) <= (0,0)))
or (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/3) <= (0,0)) and (((0,-1) + (0,0)/3) <= (0,0)))
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/3) <= (0,0)) and (((1,0) + (0,0)/3) <= (0,0)))
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/3) <= (0,0)) and (((0,-1) + (0,0)/3) <= (0,0)))
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((1,0) + (0,0)/3) <= (0,0)) and (((0,-1) + (0,0)/3) <= (0,0)))
)}                                                                                                                                                                                                                
%water from center cell when only three cells have water more than average                                                                                                                                        
rule : { (0,0) - 3*((0,0)/4) } 1000 { ((0,0) >= 0) and                                                                                                                                                           
(  (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/4) <= (0,0)) and (((0,1) + (0,0)/4) <= (0,0)) and (((1,0) + (0,0)/4) <= (0,0)))
or (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/4) <= (0,0)) and (((0,1) + (0,0)/4) <= (0,0)) and (((0,-1) + (0,0)/4) <= (0,0)))
or (((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((-1,0) + (0,0)/4) <= (0,0)) and (((1,0) + (0,0)/4) <= (0,0)) and (((0,-1) + (0,0)/4) <= (0,0)))
or (((-1,0) > (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and (((0,1) + (0,0)/4) <= (0,0)) and (((1,0) + (0,0)/4) <= (0,0)) and (((0,-1) + (0,0)/4) <= (0,0)))
) }
%water from center cell when all four cells have water more than average
rule : { (0,0) - 4*((0,0)/5) } 1000 { ((0,0) >= 0) and  ((((-1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((1,0) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5)) and ((0,-1) < (((-1,0)+(1,0)+(0,-1)+(0,1)+(0,0))/5))) and (((-1,0) + (0,0)/5) <= (0,0)) and (((0,1) + (0,0)/5) <= (0,0)) and (((1,0) + (0,0)/5) <= (0,0)) and (((0,-1) + (0,0)/5) <= (0,0))) }

rule : {(0,0) + 0.8} 1000 { t }

[setRain]
rule : { uniform(6,50) } 1000 { t }

[Rain]
distribution : exponential
mean : 40
initial : 1
increment : 0