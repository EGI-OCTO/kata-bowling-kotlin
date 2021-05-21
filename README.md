# Kata Bowling in Kotlin

Scoring rules : [see here](https://www.wikihow.com/Score-Bowling)

Usually I do this kata considering the scoring of a full game of 10 frames.    
This time I tried to consider the scoring of an on-going game.

There are different situations in which the score is pending, for example : 

`| 2, 3 | 3, ?|` > the "best displayable" score is 5, because the second frame is incomplete

`| 2, 3 | 10 |` > the "best displayable" score is 5, because the strike frame can't be scored yet (before another 2 rolls)

`| 2, 3 | 5, 5 |` > the "best displayable" score is 5, because the spare frame can't be scored yet (before another roll)

 I tried to stick as close as possible to the vocabulary of bowling and come up with a code as human readable as possible.
 
 `Scorecard` : is responsible for scoring a game.
 
 `Framescore` : is responsible for scoring a single frame.
 
 `Frame` : represents a frame (duh!) it can be composed of one or two rolls.
 
 `Roll` : represents a roll (duh!) and the number of pins that were knocked down.

