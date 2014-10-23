/*
 * myCBR License 2.0
 *
 * Copyright (c) 2009
 * Thomas Roth-Berghofer, Armin Stahl & Deutsches Forschungszentrum f&uuml;r K&uuml;nstliche Intelligenz DFKI GmbH
 * Further contributors: myCBR Team (see http://mycbr-project.net/contact.html for further information 
 * about the myCBR Team). 
 * All rights reserved.
 *
 * myCBR is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Since myCBR uses some modules, you should be aware of their licenses for
 * which you should have received a copy along with this program, too.
 * 
 * endOfLic */

package test.junittest.similarity;

import junit.framework.TestCase;

import org.junit.Test;

import de.dfki.mycbr.core.similarity.StringFct;
import de.dfki.mycbr.core.similarity.config.StringConfig;
import test.junittest.TestFramework;

/**
 * @author myCBR Team
 *
 */
public class StringFunctionTest extends TestCase {
	
	/**
	 * Test method for {@link de.dfki.mycbr.core.similarity.StringFct#calculateSimilarity(Attribute, Attribute)}.
	 */
	@Test
	public void testCalculateSimilarityEQUALITY() {
		
		try {
			TestFramework frame = new TestFramework();
			StringFct f = frame.dealerDesc.addStringFct(StringConfig.EQUALITY, "f1", true);
			assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car23")).getValue() == 0.0);
			assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car24")).getValue() == 1.00);
			
		} catch (Exception exp) {
			assertTrue("Excpetion in StringFctTest: testCalculateSimilarityEQUALITY",false);
		}
		
	}

    /**
     * Test method for {@link de.dfki.mycbr.core.similarity.StringFct#calculateSimilarity(Attribute, Attribute)}.
     */
    @Test
    public void testCalculateSimilarityNGRAM() {

        try {
            TestFramework frame = new TestFramework();
            StringFct f = frame.dealerDesc.addStringFct(StringConfig.NGRAM, "f1", true);
            
            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car23")).getRoundedValue() == 0.5);
            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 1.00);

        } catch (Exception exp) {
            assertTrue("Excpetion in StringFctTest: testCalculateSimilarityNGRAM",false);
        }

    }
    
    /**
     * Test method for {@link de.dfki.mycbr.core.similarity.StringFct#calculateSimilarity(Attribute, Attribute)}.
     */
    @Test
    public void testCalculateSimilarityLEVENSHTEIN() {

        try {
            TestFramework frame = new TestFramework();
            
            for (int i = 0; i < 2; i++) {
            	
            	StringFct f = frame.dealerDesc.addStringFct(StringConfig.LEVENSHTEIN, "f1", true);
            	
            	// Test all variations in casesens and caseinsens
            	f.setCaseSensitive(i == 0);
				
				// Base functionality
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 1.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car23")).getRoundedValue() == 0.8);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car13")).getRoundedValue() == 0.6);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Bike")).getRoundedValue() == 0.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Bike24")).getRoundedValue() == 0.33);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute(""), frame.dealerDesc.getStringAttribute("Bike24")).getRoundedValue() == 0.0);
	            
	            // UNICODE Test
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("ÆÇĐŸƃ®"), frame.dealerDesc.getStringAttribute("ÆÇĐŸƃ®")).getRoundedValue() == 1.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("ʤʐɰ"), frame.dealerDesc.getStringAttribute("ʤɰ")).getRoundedValue() == 0.67);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute(""), frame.dealerDesc.getStringAttribute("ʤɰ")).getRoundedValue() == 0.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("ʤʐɰ"), frame.dealerDesc.getStringAttribute("")).getRoundedValue() == 0.0);
	            
	            // Test configuration feature
	            f.setLevenshteinAddCost(2);
	            f.setLevenshteinChangeCost(3);
	            f.setLevenshteinDelCost(2);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 1.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car23")).getRoundedValue() == 0.4);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute(""), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 0.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("")).getRoundedValue() == 0.0);    
	            f.setLevenshteinChangeCost(6);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("Car23")).getRoundedValue() == 0.2);
	            
	            f.setLevenshteinAddCost(0);
	            f.setLevenshteinChangeCost(0);
	            f.setLevenshteinDelCost(0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car25"), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 1.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Bike123"), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 1.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute(""), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 0.0);
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car24"), frame.dealerDesc.getStringAttribute("")).getRoundedValue() == 0.0);
	                       
	            // Only positive values allowed
	            try {
	                f.setLevenshteinChangeCost(-1);
	                assertTrue(false);
	            } catch (UnsupportedOperationException ex) {
	            	assertTrue(true);
	            }
	            
	            try {
	            	f.setLevenshteinAddCost(-1);
	                assertTrue(false);
	            } catch (UnsupportedOperationException ex) {
	            	assertTrue(true);
	            }
	            
	            try {
	                f.setLevenshteinDelCost(-1);
	                assertTrue(false);
	            } catch (UnsupportedOperationException ex) {
	            	assertTrue(true);
	            }
	            
	            assertTrue(f.calculateSimilarity(frame.dealerDesc.getStringAttribute("Car25"), frame.dealerDesc.getStringAttribute("Car24")).getRoundedValue() == 1.0);
            }
        } catch (Exception exp) {
            assertTrue("Excpetion in StringFctTest: testCalculateSimilarityLEVENSHTEIN",false);
        }

    }

}
