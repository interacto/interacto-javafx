package essai1;

import net.java.games.input.*;

public class GamePadInspector {

	 
	 private ControllerEnvironment env;
	 
	 
	 /**
	 * Constructeur par defaut
	 */
	 public GamePadInspector() {
	    env = ControllerEnvironment.getDefaultEnvironment();
	 
	    Controller[] ctrls = env.getControllers();
	 
	    for(Controller c : ctrls) {
		    System.out.println("\n=>["+c.getName()+"]");
		    System.out.println("     -> Type :        "+c.getType());
		    System.out.println("     -> Port type :   "+c.getPortType());
		    System.out.println("     -> Port number : "+c.getPortNumber());
		 
		    System.out.println("\n   -Controls :");
		    Component[] cpnts = c.getComponents();
		 
		    for(Component cp : cpnts) {
		       System.out.println("     -> ["+cp.getName()+"] :");
		       System.out.println("            Identifier : "+cp.getIdentifier().getName());
		    }
	    }
	 }
	 
	 
	 
	 /**
	 * @param args
	 */
	 public static void main(String[] args) {
	    new GamePadInspector();
	 }

	}