package org.malai.model.conditionSolver

import java.util.List
import java.util.ArrayList

class Parser{

	/*
	 * Entry point
	 *
	 * Return the list of the solutions for the given expression.
	 * A solution contains all variables with their value.
	 */
	def List<String> getSolutions(String condition) {
	
		//Init the solver
		org.malai.wrapper.ChocoWrapper.initialization()
		
		//Set variables and constraints in the solver
		parse(condition,0)
		
		//Solve the expression
		org.malai.wrapper.ChocoWrapper.solve()

		//Get the solutions
		var result = new ArrayList<String>()
		
		var Object res
		
		var stop = false
		while(!stop){
//			res = org.malai.wrapper.ChocoWrapper.getCurrentSolution()
//			result.add(res)
//			
//			res = org.malai.wrapper.ChocoWrapper.getNextSolution()
//			stop = not res.asType(Boolean)
		}
		
		return result
	}

	/*
	 * Return the end position of the current condition that may be then end of the string or a char just before a ')'
	 * @position is the begin character offset of the current condition
	 */
	def int parse(String condition, int position) {
	
		var int begin = ignoreWhitespace(condition,position)
	
		var result = begin
		
		var int endParse
		
		if(condition.charAt(begin).equals("(")) {
			endParse = parseParentheses(condition,begin)
		}
		else {
			if(condition.charAt(begin).equals("!")) {
				if(condition.charAt(begin+1).equals("(")){
					endParse = parseParentheses(condition,begin+1)
				}
				else{
					endParse = parseVar(condition,begin+1)
					org.malai.wrapper.ChocoWrapper.pushVariable(condition.substring(begin+1,endParse+1))
				}
				org.malai.wrapper.ChocoWrapper.pushNOT()
			}
			else {
				endParse = parseVar(condition,begin)
				org.malai.wrapper.ChocoWrapper.pushVariable(condition.substring(begin,endParse+1))
//					stdio.writeln(condition.substring(begin,endParse+1))
			}
		}
		
		if (endParse == condition.length - 1) {
			result = endParse
		}
		else{
			var int next = ignoreWhitespace(condition,endParse+1) 
			
			if(condition.charAt(next).equals(")")) {
					result = next - 1
			}
			else{
				if (condition.charAt(next).equals("|") && condition.charAt(next+1).equals("|")){
					result = parse(condition,next + 2)	
					org.malai.wrapper.ChocoWrapper.pushOR()
				}
				else{
					if (condition.charAt(next).equals("&") && condition.charAt(next+1).equals("&"))	{
						result = parse(condition,next + 2)
						org.malai.wrapper.ChocoWrapper.pushAND()
					}	
				}	
			}
		}
		return result
	}	
	
	/*
	 * Search the close parenthese and return his position in @condition.
	 *
	 * @condition is the parsed string
	 * @position is the offset of the first parenthese in @condition 
	 */
	def int parseParentheses(String condition, int position) {

		var result = position
	
		var int endParse
		
		if(condition.charAt(position).equals("(")) {
//					stdio.writeln("[")
				var int next = ignoreWhitespace(condition,position+1)
				endParse = parse(condition,next)
		}
		
		var int next = ignoreWhitespace(condition,endParse+1)
		
		if(condition.charAt(next).equals(")")) {
//				stdio.writeln("]")
			result = next
		}	
		
		return result
	}
	
	/*
	 * Search the last character of the variable and return his position in @condition.
	 *
	 * @condition is the parsed string
	 * @position is the offset of the first char of the variable in @condition 
	 */
	def int parseVar(String condition , int position) {

		var result = position
	
		var boolean stop = false
		var int i = position
		while(!stop){
			i = i + 1
			if(i == condition.length ) {
				stop = true
				result = i - 1
			}
			else{
				if( condition.charAt(i).equals("|") 
				|| condition.charAt(i).equals("&") 
				|| condition.charAt(i).equals(" ")
				|| condition.charAt(i).equals(")")){
					stop = true
					result = i - 1
				}
				else{
					if(condition.charAt(i).equals("(")) {
						stop = true
						if (i+1<condition.length) {
							var int next = ignoreWhitespace(condition,i+1)
							if (condition.charAt(next).equals( ")")) {
								result = next
							}
						}
					}
				}
			}
		}
		return result
	}
	
	/*
	 * Search the position in @condition of the last consecutive occurrence 
	 * of a space character, from @position
	 *
	 * @position is the first space character position
	 */
	def int parseWhitespace(String condition, int position) {
	
		var result = position
		
		var stop = false
		var int i = position
		while(!(stop || i >= condition.length() - 1)){
			i = i + 1
			if (!condition.charAt(i).equals(" ")) {
				stop = true
				result = i
			}
		}
		
		return result
	}
	
	/*
	 * Return the position of the next no space character 
	 */
	def int ignoreWhitespace(String condition, int position) {
		var int result = position
		if (position < condition.length) {
			if (condition.charAt(position).equals(" ")){
				result = parseWhitespace(condition,position)
			}
		}
		return result
	}

}