package org.malai.model.aspect

import fr.inria.triskell.k3.Aspect
import java.util.ArrayList
import java.util.List
import org.malai.action.Action
import org.malai.instrument.Instrument
import org.malai.instrument.Link
import org.malai.interaction.AbortingState
import org.malai.interaction.Interaction
import org.malai.interaction.InteractionFactory
import org.malai.interaction.State
import org.malai.interaction.TerminalState
import org.malai.interaction.Transition
import org.malai.model.conditionSolver.Parser
import org.malai.model.generator.Context
import org.malai.model.generator.Generator

import static extension org.malai.model.aspect.ActionAspect.*
import static extension org.malai.model.aspect.InteractionAspect.*
import static extension org.malai.model.aspect.StateAspect.*
import static extension org.malai.model.aspect.TransitionAspect.*
import static extension org.malai.model.aspect.LinkAspect.*

@Aspect(className=typeof(Instrument))
class InstrumentAspect {

	def void visit(Context context, Generator generator) {
		println("Hello from INSTRUMENT "+_self)
		_self.links.forEach[elem | elem.visit(context, generator)]
	}
}

@Aspect(className=typeof(Link))
class LinkAspect{
	
	/*
	 * Retrieve the number of visits in this Context
	 */
	def int getVisitCounter(Context context) {
		var Integer visitCounter = context.linksCounters.get(_self)
		if(visitCounter == null){
			return 0
		}
		return visitCounter
	}
	
	/*
	 * Increment the number of visits in this Context
	 */
	def void incrVisitCounter(Context context ) {
		var int visitCounter = _self.getVisitCounter(context)
		context.linksCounters.put(_self, visitCounter+1)
	}

	/*
	 * Visits the link and creates contexts for each possibles interactions
	 *
	 * Visit the interaction and retrieve all paths to finals states
	 * - The first path update the current context
	 * - Other paths creates new contexts to be explored
	 */
	def void visit(Context context, Generator generator) {
	
		_self.incrVisitCounter(context)
		
		println("Hello from LINK "+_self)
		
		var List<List<Transition>>  paths = _self.interaction.visit(context)
		
		paths.forEach[path, index |
				var Context newContext
				if (index == 0) {
					newContext = context
					}
				else{
					newContext = context.copy
					newContext.attachNode = generator.currentNode
					generator.addContext(newContext)
				}
				
				if (path.size > 0) {
					var Transition t = path.last
					if (t.outputState instanceof TerminalState) {
						_self.action.visit(newContext) //Update context
					}
				}
			]
	}
}


@Aspect(className=typeof(Interaction))
class InteractionAspect{

	/*
	 * Visits all paths and return paths to TerminalState & AbortingState
	 */
	def List<List<Transition>>  visit(Context context) {

		println("Hello from INTERACTION "+_self.class.name)
		
		_self.states.forEach[s | s.nbVisits = 0]
		
		if(_self.initState != null) {
			var List<Transition> visitedTransitions = new ArrayList<Transition>()
			return _self.initState.visit(context,visitedTransitions)
		}
		else{
			return new ArrayList<List<Transition>>();
		}
	}
}
	
@Aspect(className=typeof(State))
class StateAspect{

	var int nbVisits
	
	//Config: change this value to change the number of allowed loops
	def int nbMaxVisits() {return 3}

	/*
	 * Return all paths to TerminalState from this State
	 */
	def List<List<Transition>> visit(Context context, List<Transition> visitedTransition) {
	
		val result = new ArrayList<List<Transition>>()
	
		if(_self.nbVisits == 0) {				
			//Spliting transitions with condition parsing
			_self.outputTransitions.forEach[elem | 
				if(elem.condition != null) {
					_self.splitTransition(elem)
				}
				]
		}
		
		if(_self.nbVisits < _self.nbMaxVisits) {
			println("Hello from STATE "+_self.name)	
			_self.nbVisits = _self.nbVisits + 1		
			_self.outputTransitions.forEach[elem | 
				//if not visitedTransition.contains(elem) then //Avoid loop in the path
					visitedTransition.add(elem)
					var List<List<Transition>> paths
					paths = elem.visit(context,visitedTransition)
					result.addAll(paths.filter[e | e.size() > 0])
					visitedTransition.remove(elem)
				//end
				]
		}		
		return result		
	}
	
	/*
	 * If a condition is linked to the transition, it will be solved and the transition
	 * will be split as many as solutions found.
	 */
	def void splitTransition(Transition t) {

		if (t.conditionSolution == null) {
			var parser = new Parser()
			var List<String> sols = parser.getSolutions(t.condition)
			sols.forEach[sol,index |
				if(index == 0) {
					t.conditionSolution = sol
					}
				else{
					var Transition newTr = t.copy()
					newTr.conditionSolution = sol
					_self.outputTransitions.add(newTr)
				}
				]
		}
	}
}

@Aspect(className=typeof(TerminalState))
class TerminalStateAspect{

	/*
	 * Return a collection containing the path to the TerminalState
	 */
	def List<List<Transition>> visit(Context context, List<Transition> visitedTransition) {
		println("Hello from TERMINAL_STATE "+_self.name)
		
		var res = visitedTransition.join("[",">","]",[e|e.name])
		println(res)
		
		var result = new ArrayList<List<Transition>>
		val List<Transition> clonedPath = new ArrayList<Transition>()
		visitedTransition.forEach[e | clonedPath.add(e)] //Protection to side effect
		result.add(clonedPath)
		return result
	}
}

@Aspect(className=typeof(AbortingState))
class AbortingStateAspect{

	/*
	 * Return empty path
	 */
	def List<List<Transition>> visit(Context context, List<Transition> visitedTransition) {
		println("Hello from ABORTING_STATE "+_self.name)
		
		var res = visitedTransition.join("[",">","]",[e|e.name])
		println(res.toString)
		
		var result = new ArrayList<List<Transition>>()
		//Return a collection containing the path to the AbordingState
		val List<Transition> clonedPath = new ArrayList<Transition>()
		visitedTransition.forEach[e | clonedPath.add(e)] //Protection to side effect
		result.add(clonedPath)
		return result;
	}
}
	
@Aspect(className=typeof(Transition))
class TransitionAspect{

	//If not void: the transition was splited to catch solutions of the condition
	var String conditionSolution

	def List<List<Transition>> visit(Context context, List<Transition> visitedTransition) {
		println("Hello from TRANSITION "+_self.name)
		
		if(_self.conditionSolution != null) {
			println("{"+_self.conditionSolution+"}")
		}
		
		return _self.outputState.visit(context,visitedTransition)
	}
	
	//Dummy clone
	def Transition copy() {
		var result = InteractionFactory.eINSTANCE.createTransition();
		result.inputState = _self.inputState
		result.outputState = _self.outputState
		result.description = _self.description
		result.event = _self.event
		result.condition = _self.condition
		result.actions = _self.actions
		result.name = _self.name
		result.hid = _self.hid
		return result
	}
	
}


@Aspect(className=typeof(Action))
class ActionAspect{

	/*
	 * Update the context
	 */
	def void visit(Context context) {
		context.addSolvedAvtion(_self)
		println("Hello from ACTION "+_self.class.name)
	}
}