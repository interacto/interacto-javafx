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
		       if (cp.getIdentifier() instanceof Component.Identifier.Button)
			       System.out.println("            Identifier : Button "+cp.getIdentifier());
		       else if (cp.getIdentifier() instanceof Component.Identifier.Axis)
			       System.out.println("            Identifier : Axis "+cp.getIdentifier());
		       else
			       System.out.println("            Identifier : Key "+cp.getIdentifier());
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