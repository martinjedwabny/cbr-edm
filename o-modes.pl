mode(gt_Non_maleficence(+,+)).
mode(gt_Self_improvement(+,+)).
mode(gt_Fidelity(+,+)).
mode(gt_Beneficence(+,+)).

base(gt_Non_maleficence(alternative,alternative)).
base(gt_Self_improvement(alternative,alternative)).
base(gt_Fidelity(alternative,alternative)).
base(gt_Beneficence(alternative,alternative)).
base(more_ethical(alternative,alternative)).

learn(more_ethical/2).
