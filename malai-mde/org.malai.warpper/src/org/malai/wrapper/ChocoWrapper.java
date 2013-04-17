package org.malai.wrapper;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.cp.solver.CPSolver;

/*
 * A wrapper of the Choco library. It could be used by a Kermeta 
 * project to solve a logical expression. All methods have to be 
 * static.
 *  
 * This class doesn't parse the expression, it just solves it.
 */
public class ChocoWrapper {
	
	/*
	 * Model of the expression
	 */
	static Model m;
	
	/*
	 * The solver
	 */
	static Solver s;
	
	/*
	 * Indicate if solutions were computed
	 */
	static boolean solved;
	
	/*
	 * Store expression parts
	 */
	static Stack<Constraint> stack;
	
	/*
	 * Store variables
	 */
	static HashMap<String,IntegerVariable> varMap;
	
	/*
	 * Initialize the warpper.
	 */
	public static void initialization(){
		m = new CPModel();
		s = new CPSolver();
		stack = new Stack<Constraint>();
		varMap = new HashMap<String,IntegerVariable>();
		solved = false;
	}
	
	/*
	 * Add the variable at the top of the stack
	 */
	public static void pushVariable(String var){
		
		IntegerVariable theVar = varMap.get(var);
		if(theVar == null){
			theVar = Choco.makeIntVar(var, 0,1);
			varMap.put(var, theVar);
		}
		//FIXME:find a better way
		Constraint res = Choco.or(theVar,theVar);
		stack.push(res);
	}
	
	/*
	 * Remove the two top elements (elem1 and elem2) from the stack.
	 * Then add on the top of the stack the "elem1 OR elem2" constraint.
	 */
	public static void pushOR(){
		Constraint elem1 = stack.pop();
		Constraint elem2 = stack.pop();
		Constraint res = Choco.or(elem1,elem2);
		stack.push(res);
	}
	
	/*
	 * Remove the two top elements (elem1 and elem2) from the stack.
	 * Then add on the top of the stack the "elem1 AND elem2" constraint.
	 */
	public static void pushAND(){
		Constraint elem1 = stack.pop();
		Constraint elem2 = stack.pop();
		Constraint res = Choco.and(elem1,elem2);
		stack.push(res);
	}
	
	public static void pushNOT(){
		Constraint elem = stack.pop();
		Constraint res = Choco.not(elem);
		stack.push(res);
	}
	
	/*
	 * Solve the current model.
	 */
	public static void solve(){
		
		if(!stack.isEmpty()){
			m.addConstraint(stack.pop());
			
			//read the model
			s.read(m);
			//solve the problem
			s.solve();
			solved = true;
		}
	}	
	
	/*
	 * Go to the next solution.
	 * 
	 * Return false if no other solution, true otherwise
	 */
	public static boolean getNextSolution(){
		return s.nextSolution();
	}
	
	/*
	 * List all variables of the current solution.
	 * 
	 * Must be call after solve().
	 */
	public static String getCurrentSolution(){
		if(solved){
			StringBuffer res = new StringBuffer();
			
			Set<Entry<String,IntegerVariable>> allVar = varMap.entrySet();
			for(Entry elem : allVar){
				res.append(elem.getKey());
				res.append(" : ");
				res.append(s.getVar((IntegerVariable)elem.getValue()).getVal());
				res.append(";");
			}
			return res.toString();
		}
		return null;
	}

}
