mode(hasFeature(+,c)).
mode(hasDuty(+,c)).

base(hasFeature(situation,feature)).
base(hasDuty(alternative,duty)).
base(more_stringent(alternative,alternative,situation)).

option(negation, off).

required(hasDuty).

learn(more_stringent/3).

situation('jesse_s14').
situation('walter_s6').
situation('walter_s7').
situation('walter_s8').
situation('jesse_s11').
situation('walter_s9').
situation('jesse_s15').
situation('jesse_s2').
situation('jesse_s3').
situation('walter_s1').
situation('jesse_s10').
situation('walter_s2').
situation('jesse_s7').
situation('jesse_s4').
situation('jesse_s5').
situation('gus_s10').
situation('jesse_s8').
situation('jesse_s9').
situation('walter_s11').
situation('walter_s10').

alternative('jesse_s4_c1_1_1_1').
alternative('jesse_s4_c1_1_1_2').
alternative('gus_s10_c1_1_1_1_1').
alternative('gus_s10_c1_1_1_1_2').
alternative('walter_s7_c5_1').
alternative('walter_s7_c5_2').
alternative('walter_s6_c6').
alternative('walter_s6_c5').
alternative('jesse_s7_c1_2_2_1').
alternative('jesse_s7_c1_2_2_2').
alternative('jesse_s9_c1_1_1_2_1').
alternative('jesse_s9_c1_1_1_2_2').
alternative('walter_s8_c6_1').
alternative('walter_s8_c6_2').
alternative('jesse_s11_c1_1_2_2_2').
alternative('jesse_s11_c1_1_2_2_1').
alternative('jesse_s3_c1_2_1').
alternative('jesse_s3_c1_2_2').
alternative('jesse_s10_c1_1_2_1_2').
alternative('jesse_s10_c1_1_2_1_1').
alternative('jesse_s8_c1_1_1_1_1').
alternative('jesse_s8_c1_1_1_1_2').
alternative('jesse_s2_c1_1_2').
alternative('jesse_s2_c1_1_1').
alternative('walter_s1_c3').
alternative('walter_s1_c4').
alternative('walter_s11_c6_2_1').
alternative('walter_s11_c6_2_2').
alternative('jesse_s5_c1_1_2_2').
alternative('jesse_s5_c1_1_2_1').
alternative('walter_s9_c5_2_1').
alternative('walter_s9_c5_2_2').
alternative('jesse_s15_c1_2_2_2_1').
alternative('jesse_s15_c1_2_2_2_2').
alternative('walter_s2_c3_1').
alternative('walter_s2_c3_2').
alternative('walter_s10_c6_1_2').
alternative('walter_s10_c6_1_1').
alternative('jesse_s14_c1_2_2_1_2').
alternative('jesse_s14_c1_2_2_1_1').

feature('immobilized(hank)').
feature('persecuted_by(police)').
feature('persecuted_by(hank)').
feature('immobilized(agent)').
feature('has_rival_business(walter)').
feature('associate(walter)').
feature('asks_help(hank)').
feature('in_danger()').
feature('proposes_deal(police)').
feature('has_something_to_hide()').
feature('police_about_to_arrive()').

duty('justice').
duty('fidelity').
duty('self_improvement').
duty('beneficence').
duty('non_maleficence').

hasFeature('jesse_s14', 'persecuted_by(police)').
hasFeature('jesse_s14', 'proposes_deal(police)').
hasFeature('jesse_s14', 'has_something_to_hide()').
hasFeature('walter_s6', 'asks_help(hank)').
hasFeature('walter_s6', 'has_something_to_hide()').
hasFeature('walter_s7', 'has_something_to_hide()').
hasFeature('walter_s8', 'has_something_to_hide()').
hasFeature('jesse_s11', 'immobilized(hank)').
hasFeature('jesse_s11', 'has_something_to_hide()').
hasFeature('walter_s9', 'has_something_to_hide()').
hasFeature('jesse_s15', 'in_danger()').
hasFeature('jesse_s15', 'has_something_to_hide()').
hasFeature('jesse_s2', 'persecuted_by(hank)').
hasFeature('jesse_s2', 'has_something_to_hide()').
hasFeature('jesse_s3', 'immobilized(agent)').
hasFeature('jesse_s3', 'has_something_to_hide()').
hasFeature('walter_s1', 'has_something_to_hide()').
hasFeature('jesse_s10', 'has_something_to_hide()').
hasFeature('jesse_s10', 'police_about_to_arrive()').
hasFeature('walter_s2', 'has_something_to_hide()').
hasFeature('jesse_s7', 'persecuted_by(hank)').
hasFeature('jesse_s7', 'has_something_to_hide()').
hasFeature('jesse_s4', 'persecuted_by(hank)').
hasFeature('jesse_s4', 'has_something_to_hide()').
hasFeature('jesse_s5', 'persecuted_by(hank)').
hasFeature('jesse_s5', 'has_something_to_hide()').
hasFeature('gus_s10', 'has_rival_business(walter)').
hasFeature('jesse_s8', 'associate(walter)').
hasFeature('jesse_s8', 'has_something_to_hide()').
hasFeature('jesse_s9', 'has_something_to_hide()').
hasFeature('walter_s11', 'has_something_to_hide()').
hasFeature('walter_s10', 'has_something_to_hide()').

hasAlternative('jesse_s14', 'jesse_s14_c1_2_2_1_2').
hasAlternative('jesse_s14', 'jesse_s14_c1_2_2_1_1').
hasAlternative('walter_s6', 'walter_s6_c6').
hasAlternative('walter_s6', 'walter_s6_c5').
hasAlternative('walter_s7', 'walter_s7_c5_1').
hasAlternative('walter_s7', 'walter_s7_c5_2').
hasAlternative('walter_s8', 'walter_s8_c6_1').
hasAlternative('walter_s8', 'walter_s8_c6_2').
hasAlternative('jesse_s11', 'jesse_s11_c1_1_2_2_2').
hasAlternative('jesse_s11', 'jesse_s11_c1_1_2_2_1').
hasAlternative('walter_s9', 'walter_s9_c5_2_1').
hasAlternative('walter_s9', 'walter_s9_c5_2_2').
hasAlternative('jesse_s15', 'jesse_s15_c1_2_2_2_1').
hasAlternative('jesse_s15', 'jesse_s15_c1_2_2_2_2').
hasAlternative('jesse_s2', 'jesse_s2_c1_1_2').
hasAlternative('jesse_s2', 'jesse_s2_c1_1_1').
hasAlternative('jesse_s3', 'jesse_s3_c1_2_1').
hasAlternative('jesse_s3', 'jesse_s3_c1_2_2').
hasAlternative('walter_s1', 'walter_s1_c3').
hasAlternative('walter_s1', 'walter_s1_c4').
hasAlternative('jesse_s10', 'jesse_s10_c1_1_2_1_2').
hasAlternative('jesse_s10', 'jesse_s10_c1_1_2_1_1').
hasAlternative('walter_s2', 'walter_s2_c3_1').
hasAlternative('walter_s2', 'walter_s2_c3_2').
hasAlternative('jesse_s7', 'jesse_s7_c1_2_2_1').
hasAlternative('jesse_s7', 'jesse_s7_c1_2_2_2').
hasAlternative('jesse_s4', 'jesse_s4_c1_1_1_1').
hasAlternative('jesse_s4', 'jesse_s4_c1_1_1_2').
hasAlternative('jesse_s5', 'jesse_s5_c1_1_2_2').
hasAlternative('jesse_s5', 'jesse_s5_c1_1_2_1').
hasAlternative('gus_s10', 'gus_s10_c1_1_1_1_1').
hasAlternative('gus_s10', 'gus_s10_c1_1_1_1_2').
hasAlternative('jesse_s8', 'jesse_s8_c1_1_1_1_1').
hasAlternative('jesse_s8', 'jesse_s8_c1_1_1_1_2').
hasAlternative('jesse_s9', 'jesse_s9_c1_1_1_2_1').
hasAlternative('jesse_s9', 'jesse_s9_c1_1_1_2_2').
hasAlternative('walter_s11', 'walter_s11_c6_2_1').
hasAlternative('walter_s11', 'walter_s11_c6_2_2').
hasAlternative('walter_s10', 'walter_s10_c6_1_2').
hasAlternative('walter_s10', 'walter_s10_c6_1_1').

hasDuty('jesse_s14_c1_2_2_1_1', 'justice').
hasDuty('walter_s7_c5_2', 'fidelity').
hasDuty('walter_s6_c6', 'fidelity').
hasDuty('jesse_s7_c1_2_2_2', 'fidelity').
hasDuty('walter_s8_c6_2', 'fidelity').
hasDuty('walter_s2_c3_1', 'fidelity').
hasDuty('walter_s10_c6_1_2', 'fidelity').
hasDuty('walter_s7_c5_1', 'self_improvement').
hasDuty('walter_s6_c5', 'self_improvement').
hasDuty('jesse_s7_c1_2_2_1', 'self_improvement').
hasDuty('jesse_s9_c1_1_1_2_1', 'self_improvement').
hasDuty('walter_s8_c6_1', 'self_improvement').
hasDuty('jesse_s10_c1_1_2_1_2', 'self_improvement').
hasDuty('jesse_s8_c1_1_1_1_1', 'self_improvement').
hasDuty('walter_s2_c3_2', 'self_improvement').
hasDuty('walter_s10_c6_1_1', 'self_improvement').
hasDuty('jesse_s14_c1_2_2_1_1', 'self_improvement').
hasDuty('jesse_s11_c1_1_2_2_1', 'beneficence').
hasDuty('walter_s1_c3', 'beneficence').
hasDuty('jesse_s11_c1_1_2_2_2', 'non_maleficence').
hasDuty('jesse_s3_c1_2_2', 'non_maleficence').
hasDuty('jesse_s5_c1_1_2_1', 'non_maleficence').
hasDuty('walter_s9_c5_2_1', 'non_maleficence').
hasDuty('jesse_s14_c1_2_2_1_2', 'non_maleficence').


0.5::more_stringent('jesse_s14_c1_2_2_1_2', 'jesse_s14_c1_2_2_1_1', 'jesse_s14').
0.5::more_stringent('jesse_s14_c1_2_2_1_1', 'jesse_s14_c1_2_2_1_2', 'jesse_s14').
0.5789473684210527::more_stringent('walter_s6_c6', 'walter_s6_c5', 'walter_s6').
0.42105263157894735::more_stringent('walter_s6_c5', 'walter_s6_c6', 'walter_s6').
0.7368421052631579::more_stringent('walter_s7_c5_1', 'walter_s7_c5_2', 'walter_s7').
0.26315789473684215::more_stringent('walter_s7_c5_2', 'walter_s7_c5_1', 'walter_s7').
0.7272727272727273::more_stringent('walter_s8_c6_1', 'walter_s8_c6_2', 'walter_s8').
0.2727272727272727::more_stringent('walter_s8_c6_2', 'walter_s8_c6_1', 'walter_s8').
0.4::more_stringent('jesse_s11_c1_1_2_2_2', 'jesse_s11_c1_1_2_2_1', 'jesse_s11').
0.6::more_stringent('jesse_s11_c1_1_2_2_1', 'jesse_s11_c1_1_2_2_2', 'jesse_s11').
0.2::more_stringent('walter_s9_c5_2_1', 'walter_s9_c5_2_2', 'walter_s9').
0.8::more_stringent('walter_s9_c5_2_2', 'walter_s9_c5_2_1', 'walter_s9').
0.7142857142857143::more_stringent('jesse_s15_c1_2_2_2_1', 'jesse_s15_c1_2_2_2_2', 'jesse_s15').
0.2857142857142857::more_stringent('jesse_s15_c1_2_2_2_2', 'jesse_s15_c1_2_2_2_1', 'jesse_s15').
0.35::more_stringent('jesse_s2_c1_1_2', 'jesse_s2_c1_1_1', 'jesse_s2').
0.65::more_stringent('jesse_s2_c1_1_1', 'jesse_s2_c1_1_2', 'jesse_s2').
0.2142857142857143::more_stringent('jesse_s3_c1_2_1', 'jesse_s3_c1_2_2', 'jesse_s3').
0.7857142857142857::more_stringent('jesse_s3_c1_2_2', 'jesse_s3_c1_2_1', 'jesse_s3').
0.5555555555555556::more_stringent('walter_s1_c3', 'walter_s1_c4', 'walter_s1').
0.4444444444444444::more_stringent('walter_s1_c4', 'walter_s1_c3', 'walter_s1').
1.0::more_stringent('jesse_s10_c1_1_2_1_2', 'jesse_s10_c1_1_2_1_1', 'jesse_s10').
0.0::more_stringent('jesse_s10_c1_1_2_1_1', 'jesse_s10_c1_1_2_1_2', 'jesse_s10').
0.1282051282051282::more_stringent('walter_s2_c3_1', 'walter_s2_c3_2', 'walter_s2').
0.8717948717948718::more_stringent('walter_s2_c3_2', 'walter_s2_c3_1', 'walter_s2').
0.36363636363636365::more_stringent('jesse_s7_c1_2_2_1', 'jesse_s7_c1_2_2_2', 'jesse_s7').
0.6363636363636364::more_stringent('jesse_s7_c1_2_2_2', 'jesse_s7_c1_2_2_1', 'jesse_s7').
0.6923076923076923::more_stringent('jesse_s4_c1_1_1_1', 'jesse_s4_c1_1_1_2', 'jesse_s4').
0.3076923076923077::more_stringent('jesse_s4_c1_1_1_2', 'jesse_s4_c1_1_1_1', 'jesse_s4').
0.7142857142857143::more_stringent('jesse_s5_c1_1_2_2', 'jesse_s5_c1_1_2_1', 'jesse_s5').
0.2857142857142857::more_stringent('jesse_s5_c1_1_2_1', 'jesse_s5_c1_1_2_2', 'jesse_s5').
0.5714285714285714::more_stringent('gus_s10_c1_1_1_1_1', 'gus_s10_c1_1_1_1_2', 'gus_s10').
0.4285714285714286::more_stringent('gus_s10_c1_1_1_1_2', 'gus_s10_c1_1_1_1_1', 'gus_s10').
0.4444444444444444::more_stringent('jesse_s8_c1_1_1_1_1', 'jesse_s8_c1_1_1_1_2', 'jesse_s8').
0.5555555555555556::more_stringent('jesse_s8_c1_1_1_1_2', 'jesse_s8_c1_1_1_1_1', 'jesse_s8').
0.5::more_stringent('jesse_s9_c1_1_1_2_1', 'jesse_s9_c1_1_1_2_2', 'jesse_s9').
0.5::more_stringent('jesse_s9_c1_1_1_2_2', 'jesse_s9_c1_1_1_2_1', 'jesse_s9').
0.631578947368421::more_stringent('walter_s11_c6_2_1', 'walter_s11_c6_2_2', 'walter_s11').
0.368421052631579::more_stringent('walter_s11_c6_2_2', 'walter_s11_c6_2_1', 'walter_s11').
0.8125::more_stringent('walter_s10_c6_1_2', 'walter_s10_c6_1_1', 'walter_s10').
0.1875::more_stringent('walter_s10_c6_1_1', 'walter_s10_c6_1_2', 'walter_s10').
