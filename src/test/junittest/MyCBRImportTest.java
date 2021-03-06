/*
 * myCBR License 3.0
 * 
 * Copyright (c) 2006-2015, by German Research Center for Artificial Intelligence (DFKI GmbH), Germany
 * 
 * Project Website: http://www.mycbr-project.net/
 * 
 * This library is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.
 * 
 * endOfLic */

package test.junittest;

import java.util.*;

import de.dfki.mycbr.util.Pair;
import junit.framework.TestCase;

import org.junit.Test;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
/**
 * tests the import functionality of XML files generated by myCBR v2.6.4 to < v3.0
 * 
 * @author myCBR Team
 * 
 */
public class MyCBRImportTest extends TestCase {

	@Test
	public void testMyCBRImport1() {

		try {
			Project project = new Project(System.getProperty("user.dir") + "/src/test/projects/MyCBRImportTest1/MyCBRImportTest_CBR_SMF.XML");

			LinkedList<Double> results;
			Concept car = project.getConceptByID("Car");
			SymbolDesc manufacturer = (SymbolDesc)car.getAttributeDescs().get("Manufacturer");
			SymbolDesc color = (SymbolDesc)car.getAttributeDescs().get("Color");
			
			DefaultCaseBase cb = (DefaultCaseBase)project.getCaseBases().get("CaseBase0");

			Retrieval r = new Retrieval(car, cb);
            r.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
			Instance q = r.getQueryInstance();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("Audi"));
			q.addAttribute(color.getName(), color.getAttribute("red"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);

			assertTrue("result should be [1.0,0.8,0.3,0.0] but is " + results, results.equals(Arrays.asList(new Double[]{1.0d, 0.8d, 0.3d, 0.0d})));
	
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("Audi"));
			q.addAttribute(color.getName(), color.getAttribute("green"));
	
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
            assertTrue("result should be [1.0,0.8,0.3,0.0] but is " + results, results.equals(Arrays.asList(new Double[]{1.0d, 0.8d, 0.3d, 0.0d})));
	
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("BMW"));
			q.addAttribute(color.getName(), color.getAttribute("red"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [1.0,0.8,0.5,0.0] but is " + results, results.equals(Arrays.asList(new Double[]{1.0d,0.8d,0.5d,0.0d})));
	
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("BMW"));
			q.addAttribute(color.getName(), color.getAttribute("green"));
	
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [1.0,0.8,0.5,0.0] but is " + results, results.equals(Arrays.asList(new Double[]{1.0d,0.8d,0.5d,0.0d})));
	
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("VW"));
			q.addAttribute(color.getName(), color.getAttribute("white"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.6,0.55,0.05,0.0] but is " + results, results.equals(Arrays.asList(new Double[]{0.6d,0.55d,0.05d,0.0d})));
	
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("VW"));
			q.addAttribute(color.getName(), color.getAttribute("yellow"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.54,0.49,0.05,0.0] but is " + results, results.equals(Arrays.asList(new Double[]{0.54d,0.49d,0.05d,0.0d})));
			
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("VW"));
			q.addAttribute(color.getName(),color.getAttribute("_undefined_"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.55,0.5,0.1,0.5] but is " + results,results.equals(Arrays.asList(new Double[]{0.55d,0.5d,0.1d,0.05d})));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Excpetion in MyCBRImportTest: testMyCBRImport", false);
		}
	}

	@Test
	public void testUsedCarsFlat() {
		try {
	        Project project = new Project(System.getProperty("user.dir") + "/src/test/projects/UsedCarsFlat/used_cars_flat_CBR_SMF.XML");
            LinkedList<Double> results;
			Concept car = project.getConceptByID("Car");
			
			
			SymbolDesc manufacturer = (SymbolDesc)car.getAttributeDescs().get("Manufacturer");
			SymbolDesc color = (SymbolDesc)car.getAttributeDescs().get("Color");

			DefaultCaseBase cb = (DefaultCaseBase)project.getCaseBases().get("CaseBase0");
			Retrieval r = new Retrieval(car, cb);
			
			Instance q = r.getQueryInstance();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("audi"));
			q.addAttribute(color.getName(), color.getAttribute("red"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.setRetrievalMethod(RetrievalMethod.RETRIEVE_K_SORTED);
			r.setK(4);
			r.start();
			
			results = printResult(r);
			assertTrue("result should be [0.29,0.29,0.29,0.29] but is " + results, results.equals(Arrays.asList(new Double[]{0.29,0.29,0.29,0.29})));
	
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("audi"));
			q.addAttribute(color.getName(), color.getAttribute("green"));
	
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.29,0.29,0.29,0.29] but is " + results, results.equals(Arrays.asList(new Double[]{0.29,0.29,0.29,0.29})));
			
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("bmw"));
			q.addAttribute(color.getName(), color.getAttribute("red"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.29,0.29,0.29,0.29] but is " + results, results.equals(Arrays.asList(new Double[]{0.29,0.29,0.29,0.29})));
			
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("bmw"));
			q.addAttribute(color.getName(), color.getAttribute("green"));
	
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.29,0.29,0.29,0.29] but is " + results, results.equals(Arrays.asList(new Double[]{0.29,0.29,0.29,0.29})));
			
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("vw"));
			q.addAttribute(color.getName(), color.getAttribute("white"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.29,0.29,0.29,0.29] but is " + results, results.equals(Arrays.asList(new Double[]{0.29,0.29,0.29,0.29})));
			
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("vw"));
			q.addAttribute(color.getName(), color.getAttribute("yellow"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			assertTrue("result should be [0.29,0.29,0.29,0.29] but is " + results, results.equals(Arrays.asList(new Double[]{0.29,0.29,0.29,0.29})));
			
			r.resetQuery();
			q.addAttribute(manufacturer.getName(), manufacturer.getAttribute("vw"));
			q.addAttribute(color.getName(),color.getAttribute("_undefined_"));
			//System.out.println("\n--------------------------- query ---------------------------------");
			//q.print();
			r.start();
			results = printResult(r);
			assertTrue("result should be [0.17,0.17,0.17,0.17] but is " + results, results.equals(Arrays.asList(new Double[]{0.17,0.17,0.17,0.17})));
			project.save();
	} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Excpetion in MyCBRImportTest: testMyCBRImport", false);
		}
	}
	
	private LinkedList<Double> printResult(Retrieval result) {
		LinkedList<Double> sims = new LinkedList<Double>();
		for (Pair<Instance, Similarity> pair : result.getResult()) {
//			System.out.println("\nSimilarity: " + pair.getSecond() + " to case:");
//			pair.getFirst();
			sims.add(pair.getSecond().getRoundedValue());
		}
		return sims;
	}

	@Test
	public void testProject() {
		try {
            new Project(System.getProperty("user.dir") + "/src/test/projects/Testproject/Testproject_CBR_SMF.XML");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
