% OPERATORS DEFINITION

% Parallel operator
:- op(550, yfx, "par").
:- op(525, yfx, "dot").

:- op(500, yfx, "plus").

plus(D, plus(DD)).
plus(D, plus(DD,T)).


%rule([[plus(a, plus(b, plus(v, plus(s))))],[c,d]], E, F, P), (P=true->plus2list(E, LL), member(Y, LL)).

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
%list2par([0], par(0)).
list2par([H], par(H)).
%list2par([0,H], par(dot(0), par(H))).
list2par([H,HH], par(H, par(HH))).
list2par([H|T1], par(H, par(HH,T))):-
 list2par(T1, par(HH,T)).

list2dot([], dot(0)).
list2dot([H], dot(H)).
list2dot([H,HH], dot(H, dot(HH))).
list2dot([H|T1], dot(H, dot(HH,T))):-
 list2dot(T1, dot(HH,T)).


 /**/
dot2list(dot(0), [0]).
dot2list(dot(0),[]).
%dot2list(dot(0), 0).
dot2list(dot(H),[H]).
dot2list(dot(H, dot(HH)),[H,HH]).
dot2list(dot(H, dot(HH,T)),[H|T1]):-
	dot2list(dot(HH,T),T1).



plus2list(plus(H, plus(HH)),[H,HH]).
plus2list(plus(H, plus(HH,T)),[H|T1]):-
	plus2list(plus(HH,T),T1).


/**/
dropElement(X,[X|T],T).
dropElement(X,[H|Xs],L):-dropElement(X,Xs,L).

member(E, [E | Xs], [], Xs).
member(E, [X | Xs], [X | L], R) :-
    member(E, Xs, L, R).


/**/
rule([plus(X, XS) | PP], EV, FS) :-
	plus2list(plus(X, XS), XSS),
	member(C, XSS),
	(atom(C)
	-> EV=C,	FS=C
	;rule([C | PP], EV, FS)).

rule([dot(X, XS) | PP], EV, FS):-
 dot2list(dot(X, XS), XSS),
 member(C, XSS, L, R),
 EV=C,
 (atom(C)
 -> FS = R ;rule([C|PP], EV, FS)).

rule([par(X, XS) | PP], EV, FS) :-
	par2list(par(X, XS), XSS),
	member(C, XSS, L, R),
	(atom(C)
	->EV=C, append([L, []], R, FS)
	;	rule([C|PP], EV, CFS),
	append(L, [CFS | R], FS)).












