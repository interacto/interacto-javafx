# 4.3.1

* fix(binding): (regression) predefined zoom binding does not work anymore (key shortcut)
* fix(fsm): (regression) key event with modifiers not correctly recycled
* fix(interaction): (regression) scroll interaction with key modifiers did not work any more

# 4.3.0

* feat(interaction): multi-clicks interaction
* fix(binding): shortcut bindings do not support command/control key correctly
* fix(interaction): double-click does not clean all data on timeout
* fix(interaction): draglock data must set target info on first click
* fix(interaction): log no propagated in double click sub interactions
* fix(interaction): the draglock did not work properly using a non-primary button
* fix(interaction): uninstall a user interaction does not clean everything
* fix(test): flaky test, due to bloody static var, fixed
* test(binding): various tests added
* clean(interaction): use the latest interaction API that simplifies the code of FSMs

# 4.2.0

* feat(binding): binding entries for multi-touch added
* fix(binding): cannot zoom out
* fix(binding): concurrent interactions did not process events in all the cases
* fix(doc): javadoc fixed
* fix(interaction): incorrect uninstallation
* doc(api): documentation added
* doc(javadoc): API javadoc added
* doc(javadoc): javadoc fixed
* test(binding): tests added
* chore(maven): checkstyle updated
* clean(interaction): code simplification


# 4.1.0

* feat(binding): can configure a binding to consume the processed events
* feat(binding): can observe newly created widget bindings (eg for testing purpose)
* feat(command): new command for changing the cursor
* feat(interaction): multi touch interactions
* feat(interaction): new JFX interactions

* change(cmd): code simplification of UI commands

* fix(binding): anon binder API may not permit the definition of the interaction in a specific case
* fix(binding): exceptions must be collected when thrown from binding routines
* fix(binding): ifHadEffects not called because of a typo
* fix(binding): incorrect key binding for windows
* fix(binding): temporarly skipping tests for build
* fix(binding): temporarly skipping tests for build
* fix(chore): fix maven build on ci
* fix(chore): jfx deps should be scope-provided
* fix(doc): java doc fix
* fix(interaction): interaction data should be separated from interaction
* fix(interaction): regression in the process of mouse enter/exit
* fix(interaction): touch data must be cleared
* fix(module): add dependency

* governance(license): headers' year updated
* doc(readme): readme file added

* chore(ci): add coverage report
* chore(deps): bump checkstyle from 8.24 to 8.29
* chore(mvn): deps updated
* chore(mvn): fix headless tests

* clean(interaction): minor optimisation
* clean(interaction): useless code removed
* clean(test): useless tests removed

* test(binding): update to use the latest interacto test API
* test(binding): uses the new Interacto testing framework
