% OPERATORS DEFINITION
:- op(550, yfx, "par").
:- op(525, yfx, "dot").
:- op(500, yfx, "plus").

/* This function converts parallel operator's sequence into process's sequence.
   par2list(+sequence of parallel operator, +process parallel's list).*/
par2list(par(0),0),!.
par2list(par(X),[X]).
par2list(par(X,Y), [X|Z]):-
	par2list(Y,Z).

/* This function converts dot operator's sequence into process's sequence.
   dot2list(+sequence of dot operator, +process dot's list).*/
dot2list(dot(X),[X]).
dot2list(dot(H, dot(HH)),[H,HH]).
dot2list(dot(H, dot(HH,T)),[H|T1]):-
	dot2list(dot(HH,T),T1).

/* This function converts plus operator's sequence into process's sequence.
	 plus2lit(+sequence of plus operator, +process plus's list).*/
plus2list(plus(H, plus(HH)),[H,HH]).
plus2list(plus(H, plus(HH,T)),[H|T1]):-
	plus2list(plus(HH,T),T1).

/* This function converts the process's sequence into parallel operator's sequence.
   list2par(+process parallel's list, -sequence of parallel operator).*/
list2par([H], par(H)).
list2par([H,HH], par(H, par(HH))).
list2par([H|T1], par(H, par(HH,T))):-
 list2par(T1, par(HH,T)).

/* This function converts the process's sequence into dot operator's sequence.
   list2dot(+process dot's list, -sequence of dot operator).*/
list2dot([H], dot(H)):- !.
list2dot([H,HH], dot(H, dot(HH))):- !.
list2dot([H|T1], dot(H, dot(HH,T))):-
 list2dot(T1, dot(HH,T)).

% first(+List, -FistElem, -List)
firstElemet([X|T],X, T).

% member(+Elem, +List, -List, -Elem).
member(E, [E | Xs], [], Xs).
member(E, [X | Xs], [X | L], R) :-
    member(E, Xs, L, R).

% rule(+InitialState, -Event, -FinalState)
rule([plus(X, XS) | PP], EV, FS) :-
	plus2list(plus(X, XS), XSS),
	member(C, XSS),
	(atom(C)
	->EV=C, FS = 0
	;rule([C | PP], EV, FS)).
	
rule([dot(X) | PP], EV, FS):-
	dot2list(dot(X), XSS),
	firstElemet(XSS, C, T),
	(atom(C)
	-> EV = C, FS = 0
	; rule([C|PP], EV, CFS),
	list2dot([CFS | T], Y),
	FS=Y).


rule([dot(X, XS) | PP], EV, FS):-
	dot2list(dot(X, XS), XSS),
 	firstElemet(XSS,C,T),
 	(atom(C) 
 	-> list2dot(T, LD),
 		EV=C,
   	FS=LD
 	; rule([C|PP], EV, CFS),
 	list2dot([CFS | T], Y),
 	FS=Y).

rule([par(X, XS) | PP], EV, FS) :-
	par2list(par(X, XS), XSS),
	member(C, XSS, L, R),
	(atom(C)
	->EV=C, 
	append(L, [0], OUT),
	append(OUT, R, OO),
	list2par(OO, FS)
	;	rule([C|PP], EV, CFS),
	append(L, [CFS | R], OO),
	list2par(OO, FS)).