alternative(foot-inaction).
alternative(doc-action).
alternative(by-inaction).
alternative(doc-inaction).
alternative(loop-action).
alternative(dri-action).
alternative(by-action).
alternative(dri-inaction).
alternative(foot-action).
alternative(loop-inaction).

quantity(1).
quantity(5).
quantity(many).

hasFeature(doc-action, personalForce).
hasFeature(dri-action, personalForce).
hasFeature(dri-inaction, personalForce).
hasFeature(foot-action, personalForce).

hasFeature(foot-inaction, save, 1).
hasFeature(foot-inaction, kill, 5).
hasFeature(doc-action, save, many).
hasFeature(doc-action, kill, 1).
hasFeature(by-inaction, save, 1).
hasFeature(by-inaction, kill, 5).
hasFeature(doc-inaction, infect, many).
hasFeature(doc-inaction, save, 1).
hasFeature(loop-action, save, 5).
hasFeature(loop-action, kill, 1).
hasFeature(dri-action, save, 5).
hasFeature(dri-action, kill, 1).
hasFeature(by-action, save, 5).
hasFeature(by-action, kill, 1).
hasFeature(dri-inaction, save, 1).
hasFeature(dri-inaction, kill, 5).
hasFeature(foot-action, save, 5).
hasFeature(foot-action, kill, 1).
hasFeature(loop-inaction, save, 1).
hasFeature(loop-inaction, kill, 5).

personalForce(A) :- alternative(A), hasFeature(A, personalForce).
not_personalForce(A) :- alternative(A), not(hasFeature(A, personalForce)).

causes_kill_save(doc-action).
causes_kill_save(loop-action).
causes_kill_save(foot-action).
not_causes_kill_save(A) :- alternative(A), not(causes_kill_save(A)).

save(A, Q) :- alternative(A), quantity(Q), hasFeature(A, save, Q).
save(A, 0) :- alternative(A), not((quantity(Q), hasFeature(A, save, Q))).
saveMore(A, B) :- alternative(A), alternative(B), save(A, QA), save(B, QB), greater(QA, QB).
kill(A, Q) :- alternative(A), quantity(Q), hasFeature(A, kill, Q).
kill(A, 0) :- alternative(A), not((quantity(Q), hasFeature(A, kill, Q))).
killMore(A, B) :- alternative(A), alternative(B), kill(A, QA), kill(B, QB), greater(QA, QB).
infect(A, Q) :- alternative(A), quantity(Q), hasFeature(A, infect, Q).
infect(A, 0) :- alternative(A), not((quantity(Q), hasFeature(A, infect, Q))).
infectMore(A, B) :- alternative(A), alternative(B), infect(A, QA), infect(B, QB), greater(QA, QB).

greater(QA,QB) :- integer(QA), integer(QB), QA > QB.
