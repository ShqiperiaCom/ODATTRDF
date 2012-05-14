/**
 * Copyright 2009 Andreas Langegger, andreas@langegger.at, Austria
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.xlwrap.opendata;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.xlwrap.XLWrapTestCase;

import at.jku.xlwrap.exec.XLWrapMaterializer;
import at.jku.xlwrap.map.MappingParser;
import at.jku.xlwrap.map.XLWrapMapping;

import com.hp.hpl.jena.rdf.model.Model;



/**
 * @author aifb
 *  konverton xls ne xml dhe rdf
 */
public class ConvertCSVtoRDF {
	 private static final Logger log = LoggerFactory.getLogger( ConvertCSVtoRDF.class );
	 
	    public static void main( String[] args ) {
	        if (false) { 
	            log.info( "Usage java ConvertCSVtoRDF  []" );
	        }
	        else {
	            try {

	            	String source = "mappings/template4.trig";
	            	log.info( "\n sourceFile=" +source);
	            	
	                XLWrapMapping map = MappingParser.parse( source );
	                //kap skedarin xls
	                log.info("ref file: " + map.getReferredFiles().toArray()[0]);

	                XLWrapMaterializer mat = new XLWrapMaterializer();
	                Model m = mat.generateModel(map);
	                setPrefixes( m );
                    //skedaret rezultat 
	                m.write(new FileOutputStream("docs/website/example/template1.n3"), "N3");
	                m.write(new FileOutputStream("docs/website/example/template1.xml"), "RDF/XML");

	                log.info("DONE");	                
	            }
	            catch (Exception e) {
	                log.error( e.getMessage(), e );
	            }
	        }
	    }

	    private static void setPrefixes( Model m ) {
	        m.setNsPrefix( "rdfs", "http://www.w3.org/2000/01/rdf-schema#" );
	        m.setNsPrefix( "rdf",  "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
	        m.setNsPrefix( "xsd",  "http://www.w3.org/2001/XMLSchema#" );
	        m.setNsPrefix( "owl",  "http://www.w3.org/2002/07/owl#" );
	        m.setNsPrefix( "dc",   "http://purl.org/dc/elements/1.1/" );
	        m.setNsPrefix( "scv",  "http://purl.org/NET/scovo#" );
	        m.setNsPrefix( "ckan", "http://ckan.net/ns#" );
	        m.setNsPrefix( "sdx",  "http://epimorphics.com/vocab/sdx#" );
	        m.setNsPrefix( "foaf", "http://xmlns.com/foaf/0.1/" );
	    }

}
