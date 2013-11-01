package org.malai.model.generator

import java.util.List
import java.util.Hashtable
import org.malai.instrument.Instrument
import org.malai.instrument.Link
import java.util.ArrayList
import org.malai.action.Action

import static extension org.malai.model.aspect.LinkAspect.*

/*
 * Store all created context and for each select the next link to be visited
 */
class Generator
{
	//Instruments from the system
	var List<Instrument> allInstruments
	
	/* 
	 * Contexts created by visited Links
	 *
	 * Each interaction adds solved action/ activated instrument
	 * and then creates a new Context
	 */
	var List<Context> contexts
	
	/*
	 * Hashtable to store computed Graph from a Link
	 * Avoid to generate Graph each time of Link.visit()
	 */
	var Hashtable<Link,Graph> graphTable
	
	//Help to "attach" context to each other
	public var GraphNode currentNode

	/*
	 * Entry point
	 * 
	 * Creates the initial context and visits it, which will creates new contexts
	 * Then do the same for created contexts
	 *
	 * Return the Event Flow Graph corresponding to the possible executions
	 */
	def Graph run(List<Instrument> instr){
	
		var result = new Graph
	
		var Context newContext = initialize(instr)
		addContext(newContext)
		
		var Context currentContext
		while (!contexts.isEmpty){
			currentContext = contexts.head
			println(currentContext.toString)
		
			var Link currentLink
			var stop = false
			while(!stop){
				currentLink = currentContext.nextLink()
				if(currentLink == void) {
					stop = true
					}
				else{	
					if(currentContext.attachNode == Void) {
						//Init graph
						currentContext.attachNode = new GraphNode()
						currentContext.attachNode.relatedLink = currentLink
						result.rootNode = currentContext.attachNode
						}
					else{
						//Update graph
						currentNode = currentContext.attachNode
						var GraphNode nextNode = new GraphNode()
						nextNode.relatedLink = currentLink
						currentNode.childrenNode.add(nextNode)
						currentNode = nextNode		
					}
					currentLink.visit(currentContext, this)
				}
			}
			
			contexts.remove(currentContext)
		}
		
		return result
	}
	
	/*
	 * Retrieve instruments from the system
	 */
	def Context initialize( List<Instrument> instr) {
	
		cache4Links(instr)
		
		allInstruments.addAll(instr)
		var result = new Context()
		result.initialize(allInstruments.filter[e | e.initiallyActivated] as List, new ArrayList<Action>())
		return result
	}
	
	/*
	 * Convert all Links of all Instruments to graph and store them 
	 * into a cache
	 */
	def void cache4Links(List<Instrument> instr) {
	
		graphTable = new Hashtable<Link,Graph>()
		
		instr.forEach[i |
			i.links.forEach[l |
				var Graph gr = new Graph
				gr.rootNode.convertLink(l)
				graphTable.put(l,gr)
			]
		]
	
	}
	
	def void addContext(Context context) {
		contexts.add(context)
	}

}