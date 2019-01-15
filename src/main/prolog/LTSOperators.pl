/* Operators' definition */
:- op(550, yfx, "par").
:- op(525, yfx, "dot").
:- op(500, yfx, "plus").


/*General base rule to process the AdP
 rule(+initialState, -Event, -FinalState)*/
rule(S, E, D) :- rule(S, E, _, D).
 
/*rule(+initialState, -Event, -Action, -FinalState)*/
rule(S, E, R, D) :-
  rule(R),
  Goal =.. [R, S, E, D],
  Goal.
  
/* 
This function converts parallel operator's sequence into process's sequence.
par2list(+sequence of parallel operator, -process parallel's list).
*/
par2list(par(0),0),!.
par2list(par(X),[X]).
par2list(par(X,Y), [X|Z]):-
	par2list(Y,Z).

/* 
This function converts dot operator's sequence into process's sequence.
dot2list(+sequence of dot operator, -process dot's list).
*/
dot2list(dot(X),[X]).
dot2list(dot(H, dot(HH)),[H,HH]).
dot2list(dot(H, dot(HH,T)),[H|T1]):-
	dot2list(dot(HH,T),T1).

/* 
This function converts plus operator's sequence into process's sequence.
plus2lit(?sequence of plus operator, ?process plus's list).
*/
plus2list(plus(H, plus(HH)),[H,HH]).
plus2list(plus(H, plus(HH,T)),[H|T1]):-
	plus2list(plus(HH,T),T1).

/*
This function converts the process's sequence into parallel operator's sequence.
list2par(+process parallel's list, -sequence of parallel operator).
*/
list2par([H], par(H)).
list2par([H,HH], par(H, par(HH))).
list2par([H|T1], par(H, par(HH,T))):-
 list2par(T1, par(HH,T)).

/*
This function converts the process's sequence into dot operator's sequence.
list2dot(+process dot's list, -sequence of dot operator).
*/
list2dot([H], dot(H)):- !.
list2dot([H,HH], dot(H, dot(HH))):- !.
list2dot([H|T1], dot(H, dot(HH,T))):-
 list2dot(T1, dot(HH,T)).

/* 
This function return lists' head  
first(+ListInput, -FistElem, -RemainList)
*/
firstElement([X|T],X, T).

/*
This function is abstraction of the member method. Return all element present in a list. 
There are:
- right list: contains all the elements that appear before +Elem
- left list: contains all the elements that appear after +Elem
member(-Elem, +List, -RightList, -LeftList).
*/
member(E, [E | Xs], [], Xs).
member(E, [X | Xs], [X | L], R) :-
    member(E, Xs, L, R).

/*
This functions implements all operations' rules
rule(+InitialState, -Event, -FinalState)
*/
rule(ruleBasic).
ruleBasic(plus(X, XS), EV, FS) :-
	plus2list(plus(X, XS), XSS),
	member(C, XSS),
	(atom(C)
	->EV=C, FS = 0
	;ruleBasic(C, EV, FS)).
	
ruleBasic(dot(X), EV, FS):-
	dot2list(dot(X), XSS),
	firstElement(XSS, C, T),
	(atom(C)
	-> EV = C, FS = 0
	; ruleBasic(C, EV, CFS),
	list2dot([CFS | T], Y),
	FS=Y).
ruleBasic(dot(X, XS), EV, FS):-
	dot2list(dot(X, XS), XSS),
 	firstElement(XSS,C,T),
 	(atom(C) 
 	-> list2dot(T, LD),
 		EV=C,
   	FS=LD
 	; ruleBasic(C, EV, CFS),
 	list2dot([CFS | T], Y),
 	FS=Y).

ruleBasic(par(X, XS), EV, FS) :-
	select(par(X, XS), C, L, R),
	(atom(C)
	->EV=C,
	append(L, [0], OUT),
	append(OUT, R, OO),
	list2par(OO, FS)
	;	ruleBasic(C, EV, CFS),
	append(L, [CFS | R], OO),
	list2par(OO, FS)).

/*
This predicate selects the element into all processes
select(+parSequence, -currentElem, -LeftList, - RightList)
*/
select(X, C, L, R):-
	par2list(X, XSS),
	member(C, XSS, L, R).

