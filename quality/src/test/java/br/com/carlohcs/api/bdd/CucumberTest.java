package br.com.carlohcs.api.bdd;

import org.junit.platform.suite.api.IncludeEngines;
//import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
// Doesn't work with this annotation
//@SelectClasspathResource("src/test/resources/features")
public class CucumberTest {
}