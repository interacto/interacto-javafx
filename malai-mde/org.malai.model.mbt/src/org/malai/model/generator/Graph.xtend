package org.malai.model.generator

import org.malai.instrument.Link
import java.util.List
import java.util.ArrayList
import org.malai.interaction.Transition
import fr.inria.IAFlowGraph.InteractionTransition

import static extension org.malai.model.aspect.InteractionAspect.*
import org.malai.interaction.InteractionFactory
import fr.inria.IAFlowGraph.IAFlowGraphFactory
import org.malai.interaction.TerminalState
import org.malai.interaction.AbortingState

class Graph{
	public GraphNode rootNode
}

class GraphNode
{
	public Link relatedLink //Interaction to be translated to EFG
	public List<GraphNode> childrenNode  //Following accessible Links

	List<InteractionTransition> initialTransitions
	List<InteractionTransition> terminalTransitions
		
	/* 
	 * Convert an Interaction to a FlowGraph.
	 * The collection returned is the InteractionTransitions accessible
	 * from the source of the flow graph. 
	 */
	public def void convertLink(Link link) {
	
		initialTransitions = new ArrayList<InteractionTransition>()
		terminalTransitions = new ArrayList<InteractionTransition>()
		
		var List<List<Transition>> paths = link.interaction.visit(new Context()) //need context parameter, but useless here
		
		paths.forEach[ p | addPathInGraph(initialTransitions, p)]
		
		terminalTransitions.filter[ tr | tr.action.actionProduced].forEach[tr | tr.action.concreteAction = link.action]
	}
	
	/* 
	 * Add recursively Transitions of the path in the graph.
	 *
	 * @outgoingTransitions Transitions from the current node
	 * @path Ordered transitions to be added in the graph
	 */
	def void addPathInGraph(List<InteractionTransition> outgoingTransitions, List<Transition> path) {
	
		if(path.size > 0) {
		
			if(! outgoingTransitions.exists[e |e.concreteTransition.equals(path.get(0))] ) {
				var InteractionTransition tr = IAFlowGraphFactory.eINSTANCE.createInteractionTransition()
				tr.concreteTransition = path.get(0)
				outgoingTransitions.add(tr)
			}
			
			var InteractionTransition firstTransition = outgoingTransitions.findFirst[e | e.concreteTransition.equals(path.get(0)) ]
		
			if(path.size == 1) { //Stop case
				if(path.get(0) instanceof TerminalState ){
					firstTransition.action = IAFlowGraphFactory.eINSTANCE.createResultingAction()
					firstTransition.action.actionProduced = true
					terminalTransitions.add(firstTransition)
				}
				if(path.get(0) instanceof AbortingState ) {
					firstTransition.action = IAFlowGraphFactory.eINSTANCE.createResultingAction()
					firstTransition.action.actionProduced = false
					terminalTransitions.add(firstTransition)
				}
			}
			else { //Recursion
				var List<Transition> newPath = new ArrayList<Transition>();
				newPath.addAll(path);
				newPath.remove(0);
				addPathInGraph(firstTransition.outgoingTransitions, newPath)
			}
		}
	}
}