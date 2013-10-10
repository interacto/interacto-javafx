package org.malai.model.generator

import org.malai.action.Action
import java.util.List
import org.malai.instrument.Instrument
import org.malai.instrument.Link
import java.util.Hashtable

import static extension org.malai.model.aspect.LinkAspect.*

/*
 * Represents the runtime state
 */
class Context
{		

	//Actions created by visited Links
	public List<Action> resolvedActions
	
	//Instruments activated by visited Actions
	public List<Instrument> activatedInstr
	
	//Visits counter for each Link 
	public Hashtable<Link,Integer> linksCounters
	
	//PARAMETER
	private int MAXVISITS
	
	//Root used to attach futur nodes of the context
	public GraphNode attachNode
	
	
	/*
	 * Setup the context
	 */
	def void initialize(List<Instrument> activInstr, List<Action> resAction ) {
		MAXVISITS = 3
		resolvedActions.addAll(resAction)
		activatedInstr.addAll(activInstr)
		linksCounters = new Hashtable<Link,Integer>
	}
	
	/*
	 * Select the next Link to be visited from 
	 * all instruments activated
	 * 
	 * Strategy :
	 * Get the less visited link (and visitable)
	 * If none return void
	 */
	def Link nextLink() {	

		val findMinLink = [Link l1, Link l2 | if(l1.getVisitCounter(this) < l2.getVisitCounter(this)){ l1 } else { l2 }]
		val visitableMask = [Link link | isVisitable(link) && link.getVisitCounter(this) < MAXVISITS]
		
		//Get all links, keep just visitables and select the less visited
		return activatedInstr.map[instr| instr.links].flatten.filter(visitableMask).reduce(findMinLink)
	}
	
	/*
	 * Add an executed action in the current context
	 */
	def void addSolvedAvtion(Action act) {
		resolvedActions.add(act)
	}
	
	/*
	 * Add a usable instrument 
	 */
	def void activateInstrument(Instrument instr) {
		activatedInstr.add(instr)
	}		

	/*
	 * Remove an instrument
	 */ 
	def inactivateInstrument(Instrument instr) {
		activatedInstr.remove(instr)
	}
	
	/*
	 * Return true if all dependencies of the link are resolved
	 */
	def boolean isVisitable(Link link) {
		return link.action.dependencies.forall[dep | resolvedActions.contains(dep.srcAction)]
	}
	
	/*
	 * Clone the current instance of Context
	 */
	def Context copy(){
		var result = new Context()
		result.initialize(this.activatedInstr, this.resolvedActions)
		return result
	}
	
	override String toString() {
		val res = new StringBuffer()
	
		res.append("-----------------------------------------\n")
		activatedInstr.forEach[ i | res.append("{"+ i.class.name +"}\n")]
		resolvedActions.forEach[a | res.append("["+ a.class.name +"]\n")]
		res.append("-----------------------------------------\n")
		
		return res.toString()
	}
}