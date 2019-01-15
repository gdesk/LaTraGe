:- op(990, xfx, matches).
:- op(500, yfx, "u").

:- op(550, yfx, "par").
:- op(525, yfx, "dot").
:- op(500, yfx, "plus").

commutative('plus').
commutative('u').
commutative('par').

associative('plus').
associative('u').
associative('par').
associative('dot').

neutral('plus', no_choice).
neutral('dot', pass).
neutral('u', empty).

operator(X) :- commutative(X); associative(X).


matches(X, X).

matches(X, Y) :-
  (X =.. [Op, X1, X2], Y =.. [Op, Y1, Y2];
  Y =.. [Op, Y1, Y2], X =.. [Op, X1, X2]),
  matches(X1, Y1), 
  matches(X2, Y2), !.

matches(X, Y) :-
  (X =.. [Op, X1, X2], Y =.. [Op, Y1, Y2];
  Y =.. [Op, Y1, Y2], X =.. [Op, X1, X2]),
  commutative(Op),
  matches(X1, Y2), 
  matches(X2, Y1).

matches(X, Y) :-
  X =.. [Op, A, B],
  neutral(Op, Z),
  member(Z, [A, B]),
  delete(Z, [A, B], [X1]),
  matches(X1, Y).

matches(X, Y) :-
  Y =.. [Op, A, B],
  neutral(Op, Z),
  member(Z, [A, B]),
  delete(Z, [A, B], [Y1]),
  matches(X, Y1).

matches(X, Y) :-
  X =.. [Op, A, B],
  associative(Op),
  Y =.. [Op, Y1, Y2],
  A =.. [Op, C, D],
  A1 =.. [Op, D, B],
  matches(C, Y1),
  matches(A1, Y2).

matches(X, Y) :-
  X =.. [Op, A, B],
  associative(Op),
  Y =.. [Op, Y1, Y2],
  B =.. [Op, C, D],
  B1 =.. [Op, D, B],
  matches(A, Y1),
  matches(B1, Y2).

matches(X, Y) :-
  Y =.. [Op, A, B],
  associative(Op),
  X =.. [Op, X1, X2],
  A =.. [Op, C, D],
  A1 =.. [Op, D, B],
  matches(X1, C),
  matches(X2, A1).

matches(X, Y) :-
  Y =.. [Op, A, B],
  associative(Op),
  X =.. [Op, X1, X2],
  B =.. [Op, C, D],
  B1 =.. [Op, D, B],
  matches(X1, A),
  matches(X2, B1).

rule(S, E, D) :- rule(S, E, _, D).

rule(S, E, R, D) :-
  rule(R),
  Goal =.. [R, S, E, D],
  Goal.

rule(out).
out(Source, event(out(T)), Dest) :-
  Source matches (out(T) dot P par Ps par TS),
  ground(T),
  Dest = (P par Ps par TS u T).

rule(in).
in(Source, event(in(T)), Dest) :-
  Source matches (in(T) dot P par Ps par TS u T),
  T \= empty,
  Dest = (P par Ps par TS).

rule(rd).
rd(Source, event(rd(T)), Dest) :-
  Source matches (rd(T) dot P dot Ps dot TS u T),
  T \= empty,
  Dest = (P par Ps par TS u T).

rule(choice_l).
choice_l(Source, E, Dest) :-
  Source matches ((A plus _) dot P par Ps par TS),
  rule((A dot P par Ps par TS), E, Dest).

rule(choice_r).
choice_r(Source, E, Dest) :-
  Source matches ((_ plus B) dot P par Ps par TS),
  rule((B dot P par Ps par TS), E, Dest).

% rule((in(T) dot stop par out(t) dot stop par empty), E, D).