% OPERATORS DEFINITION

% Parallel operator
:- op(550, yfx, "par").
:- op(525, yfx, "dot").

:- op(500, yfx, "plus").


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

list2dot([], 0).
list2dot([H], H).
list2dot([H,HH], dot(H, dot(HH))).
list2dot([H|T1], dot(H, dot(HH,T))):-
 list2dot(T1, dot(HH,T)).


 /**/
dot2list(dot(0), [0]).
dot2list(dot(0),[]).
dot2list(dot(H),[H]).
dot2list(dot(H, dot(HH)),[H,HH]).
dot2list(dot(H, dot(HH,T)),[H|T1]):-
	dot2list(dot(HH,T),T1).



plus2list(plus(H, plus(HH)),[H,HH]).
plus2list(plus(H, plus(HH,T)),[H|T1]):-
	plus2list(plus(HH,T),T1).


member(E, [E | Xs], [], Xs).
member(E, [X | Xs], [X | L], R) :-
    member(E, Xs, L, R).


/**/
rule([plus(X, XS) | PP], EV, FS) :-
	plus2list(plus(X, XS), XSS),
	member(C, XSS),
	(atom(C)
	-> EV=C, FS=0
	;rule([C | PP], EV, FS)).

rule([dot(X, XS) | PP], EV, FS):-
 dot2list(dot(X, XS), XSS),
 Q=0,
 member(C, XSS, L, R),
 EV=[C, '>'],
 (atom(C)
 -> list2dot(R, LD),
   	FS=LD
 ; rule([C|PP], EV, CFS),
 list2dot([CFS | R], Y),
 append(L, Y, FS)).

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












