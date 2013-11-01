package org.malai.model.main

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import java.util.List
import org.malai.instrument.Instrument
import java.util.ArrayList
import org.malai.model.generator.Generator
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.malai.instrument.InstrumentPackage

class MainClass
{
	def void mainOperation() {	
		println("Hello from mainOperation !")
		
		var String mm_uri = "platform:/resource/org.malai.model/src/main/metamodel/instrument.ecore"
		var String m_uri = "platform:/resource/org.malai.model.examples/src/main/model/latexdraw/instruments/editingSelector.instrument"
		
		visitModel(loadModel(m_uri))
	}
	
	def void visitModel(List<Instrument> allInst) {
		
		var Generator gen  = new Generator()
		gen.run(allInst)
		
		//var myCondition : String := "  (var1==var5)   &&(!var2 ||  (var3( )&& var4) )   "
		/*var myCondition : String := "(var1 && !var2) || (!var1 && var2)"
		var parser : org::malai::conditionSolver::Parser := org::malai::conditionSolver::Parser.new
		var sols : Collection<String> := parser.getSolutions(myCondition)
		sols.each{e | stdio.writeln(e)}*/
		
	}
	
	def List<Instrument> loadModel(String path){
		var fact = new XMIResourceFactoryImpl
		if (!EPackage.Registry.INSTANCE.containsKey(InstrumentPackage.eNS_URI)) {
			EPackage.Registry.INSTANCE.put(InstrumentPackage.eNS_URI, InstrumentPackage.eINSTANCE);
		}
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", fact);

		var rs = new ResourceSetImpl()

		var uri = URI.createURI(path);
		var resource = rs.getResource(uri, true);
		val List<Instrument> allInst = new ArrayList<Instrument>()
		resource.getContents.forEach [ elem |
			if (elem instanceof Instrument) {
				var Instrument inst = elem as Instrument
				allInst.add(inst)
			}
		]
		return allInst
	}
}