mode(better_for_Beneficence(+,+)).
mode(better_for_Fidelity(+,+)).
mode(equal_for_Self_improvement(+,+)).
mode(better_for_Self_improvement(+,+)).

base(better_for_Beneficence(alternative,alternative)).
base(better_for_Fidelity(alternative,alternative)).
base(equal_for_Self_improvement(alternative,alternative)).
base(better_for_Self_improvement(alternative,alternative)).
base(more_ethical(alternative,alternative)).

learn(more_ethical/2).
