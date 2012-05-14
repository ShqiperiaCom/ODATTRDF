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

import java.io.File;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jku.xlwrap.exec.XLWrapMaterializer;
import at.jku.xlwrap.map.MappingParser;
import at.jku.xlwrap.map.XLWrapMapping;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Julia Hoxha
 * 
 */
public class ConvertCSVtoRDFTemplate1 {
	private static final Logger log = LoggerFactory.getLogger(ConvertCSVtoRDFTemplate1.class);
	public ConvertCSVtoRDFTemplate1(){
		
		
	}
	
	public int convert(File file, Object templ, JTextArea mylog){
		String xlsFileName = file.getName();
		log.info("\n xlsFileName=" + file.getAbsolutePath());
		
		String indicator = xlsFileName.replace(".xls", "");
		mylog.append("Indicator: "+ indicator + ".\n" ); 
		//log.info("\n indicator=" + indicator);
		
		// String sourceFile = "mappings/files/"+indicator+".xls";
		//sourceFile = "file:///J:/workspace/xlwrap/mappings/files/YearlyInflationRate.xls";
		String sourceFile = "file:///"+file.getAbsolutePath();
		
		log.info("\n sourceFile=" + sourceFile);
		String outFileN3 = "docs/website/example/"+indicator+".n3";
		mylog.append("Destination .n3 file: "+ outFileN3 + "\n" ); 
		String outFileXML = "docs/website/example/"+indicator+".xml";
		mylog.append("Destination .xml file: "+ outFileXML + "\n" ); 
		{
			try {

				String mappingFile = "mappings/" + templ + ".trig"; 
				log.info("\n sourceFile=" + sourceFile+ ", mappingFile=" + mappingFile);
				XLWrapMapping map = MappingParser.parse(mappingFile, sourceFile);
				// Set<String> refFiles= map.getReferredFiles();
				log.info("ref file: " + map.getReferredFiles().toArray()[0]);

				XLWrapMaterializer mat = new XLWrapMaterializer();
				Model m = mat.generateModel(map);
				setPrefixes(m);

				OutputStream out = new FileOutputStream(outFileN3);
				m.write(out, "N3");

				m.write(new FileOutputStream(outFileXML),"RDF/XML");
				
				log.info("DONE");
				return 1;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return 0;
			}
		}

		//return 1;
	}

	public static void main(String[] args) {
		if (args == null)
			try {
				throw new Exception("Required property fileName  not found for xl:Mapping ");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	  String arg = args[0];
	  String arg2 = args[1];
	  String arg3 = args[2];
	  ConvertCSVtoRDFTemplate1 converter = new ConvertCSVtoRDFTemplate1();
	  int result = converter.convert(new File(arg),arg2,new JTextArea(arg3));
	}

	private static void setPrefixes(Model m) {
		m.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		m.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		m.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
		m.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
		m.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");
		m.setNsPrefix("scv", "http://purl.org/NET/scovo#");
		m.setNsPrefix("ckan", "http://ckan.net/ns#");
		m.setNsPrefix("sdx", "http://epimorphics.com/vocab/sdx#");
		m.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		m.setNsPrefix("oda", "http://open.data.al/oda.owl#");
		m.setNsPrefix("sdmx-measure", "http://purl.org/linked-data/sdmx/2009/measure#");
	}

}
