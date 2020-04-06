package com.pokersimples.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.pokersimples.parser.handhistory.HandHistoryParserTestSuite;
import com.pokersimples.parser.handparser.HandParserTestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	HandHistoryParserTestSuite.class,
	HandParserTestSuite.class
})

public class ParserTestSuite {  
}  	