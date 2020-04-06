package com.pokersimples.parser.handparser;

import java.util.List;

import com.learn.PokerSimulator.utils.Logger;
import com.pokersimples.bo.Card;
import com.pokersimples.parser.handparser.HandParser;

import junit.framework.TestCase;

public class TestHandParserParseRange extends TestCase {

	HandParser parser = new HandParser();
	
    public void test1() {
    	List<List<Integer>> range = parser.parseRange("random");
    	
    	assertEquals(1326, range.size());
    }

    
    public void test2() {
    	List<List<Integer>> range = parser.parseRange("AKo");
    	
    	assertEquals(12  , range.size());
    }
    
    public void test3() {
    	List<List<Integer>> range = parser.parseRange("ATo+");
    	
    	assertEquals(12*4, range.size());
    }    
    
    public void test4() {
    	List<List<Integer>> range = parser.parseRange("JTs");
    	
    	assertEquals(4, range.size());
    }      
   
    public void test5() {
    	List<List<Integer>> range = parser.parseRange("ATs+");
    	
    	assertEquals(4*4, range.size());
    }          
    
    public void test6() {
    	List<List<Integer>> range = parser.parseRange("66");
    	
    	assertEquals(6, range.size());
    }      

    public void test7() {
    	List<List<Integer>> range = parser.parseRange("66+");
    	
    	assertEquals(6 * 9, range.size());
    }   
    
    public void test8() {
        int cardD2 = Card.parse("2d").ordinal();
        int cardC2 = Card.parse("2c").ordinal();
    	List<List<Integer>> range = parser.parseRange("2c2d");
    	
    	List<Integer> holeCards = range.get(0);
    	
    	assertEquals((int)cardC2, (int)holeCards.get(0));
    	assertEquals((int)cardD2, (int)holeCards.get(1));    	
    }     
}
