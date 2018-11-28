% OPERATORS DEFINITION

% Parallel operator
:- op(550, yfx, "par").
:- op(525, xfx, "dot").

/* This function converts the sequence of parallel operator into list.
   par2list(+sequence of parallel operator,-process parallel's list).*/
par2list(par(H, par(HH)),[H,HH]).
par2list(par(H, par(HH,T)),[H|T1]):-
	par2list(par(HH,T),T1).

/* This function converts the process's sequence into parallel operator's sequence.
   list2par(+process parallel's list, -sequence of parallel operator).*/
list2par([H,HH], par(H, par(HH))).
list2par([H|T1], par(H, par(HH,T))):-
 list2par(T1, par(HH,T)).

 /**/
list2dot(dot(H, dot(HH)),[H,HH]).
list2dot(dot(H, dot(HH,T)),[H|T1]):-
	list2dot(dot(HH,T),T1).

/**/
dropElement(X,[X|T],T).
dropElement(X,[H|Xs],L):-dropElement(X,Xs,L).

/**/
rule_parallel(IS, EV, FS) :-
	par2list(IS, L),
	member([X|PP], L),
	EV=X,
	delete(X, L, NL),
 	list2par(NL, PPS),
 	FS = PPS.


rule_dot(IS, EV, FS) :-
	list2dot(IS, L),
	/*member(X, L),
	EV=X,*/
	dropElement(X, L, NL),
	EV=X,
 	FS = NL.

%par2list(par([a], par([e])), L), member([X | P1], L).
