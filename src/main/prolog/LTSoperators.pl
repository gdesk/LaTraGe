% OPERATORS DEFINITION

% Parallel operator
:- op(550, yfx, "par").

% Dot operator 
:- op(525, xfx, "dot").

/* This function converts the sequence of parallel operator into list.
   par2list(+sequence of parallel operator,-process parallel's list).*/
par2list(par(0),0),!.
par2list(par(X),[X]).
par2list(par(X,Y), [X|Z]):-
	par2list(Y,Z).
	
/* This function converts the process's sequence into parallel operator's sequence.
   list2par(+process parallel's list, -sequence of parallel operator).*/
list2par([H], par(H)).
list2par([H,HH], par(H, par(HH))).
list2par([H|T1], par(H, par(HH,T))):-
 list2par(T1, par(HH,T)).

/* This function converts the process's sequence into dot operator's sequence.
   list2dot(+process dot's list, -sequence of dot operator).*/
list2dot([], dot(0)).
list2dot([H], dot(H)).
list2dot([H,HH], dot(H, dot(HH))).
list2dot([H|T1], dot(H, dot(HH,T))):-
 list2dot(T1, dot(HH,T)).

/* This function converts the sequence of dot operator into list.
   dot2list(+sequence of dot operator,-process dot's list).*/
dot2list(0, [0]).
dot2list(dot(H),[H]).
dot2list(dot(H, dot(HH)),[H,HH]).
dot2list(dot(H, dot(HH,T)),[H|T1]):-
	dot2list(dot(HH,T),T1).

/*General purpose rule to process input process algebra.
	rule(+initial state, -event, -final state)*/
rule(IS, EV, FS) :-
	member([X|PP], IS),
	EV=X,
 	FS = PP.
