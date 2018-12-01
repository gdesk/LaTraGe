% OPERATORS DEFINITION

% Parallel operator
:- op(550, yfx, "par").
:- op(525, xfx, "dot").

/* This function converts the sequence of parallel operator into list.
   par2list(+sequence of parallel operator,-process parallel's list).*/
par22list(par(H, par(HH)),[H,HH]).
par22list(par(H, par(HH,T)),[H|T1]):-
	par22list(par(HH,T),T1).



par2list(par(0),0),!.
par2list(par(X),[X]).
%par2list(par(0, T), [[0]|Z]):-
	%par2list(T, Z),!.
par2list(par(X,Y), [X|Z]):-
	par2list(Y,Z).
	

/* This function converts the process's sequence into parallel operator's sequence.
   list2par(+process parallel's list, -sequence of parallel operator).*/
list2par([0], par(0)).
list2par([H], par(H)).
list2par([0,H], par(0, par(H))).
list2par([H,HH], par(H, par(HH))).
list2par([H|T1], par(H, par(HH,T))):-
 list2par(T1, par(HH,T)).

list2dot([], 0).
list2dot([H], dot(H)).
list2dot([H,HH], dot(H, dot(HH))).
list2dot([H|T1], dot(H, dot(HH,T))):-
 list2dot(T1, dot(HH,T)).


 /**/
dot2list(0, [0]).
dot2list(dot(0), [0]),!.
dot2list(dot(H),[H]).
dot2list(dot(H, dot(HH)),[H,HH]).
dot2list(dot(H, dot(HH,T)),[H|T1]):-
	dot2list(dot(HH,T),T1).



/**/
dropElement(X,[X|T],T).
dropElement(X,[H|Xs],L):-dropElement(X,Xs,L).

/**/
rule_parallel(IS, EV, FS) :-
	%par2list(IS, L),
	member([X|PP], IS),
	
	EV=X,
	/*delete(X, L, NL),
 	list2par(NL, PPS),*/
 	FS = PP.


rule_dot(IS, EV, FS) :-
	list2dot(IS, L),
	/*member(X, L),
	EV=X,*/
	dropElement(X, L, NL),
	EV=X,
 	FS = NL.

%par2list(par([a], par([e])), L), member([X | P1], L).
