package br.unisinos.swe.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.talis.hbase.rdf.HBaseRdfFactory;
import com.talis.hbase.rdf.Store;
import com.talis.hbase.rdf.StoreDesc;
import com.talis.hbase.rdf.connection.HBaseRdfConnection;
import com.talis.hbase.rdf.layout.simple.StoreSimple;
import com.talis.hbase.rdf.store.LayoutType;

public class MainTest {

	static String userHome = "";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		userHome =System.getProperty("user.home");
		Store simpleStore = getStoreSimple();
		Model base = HBaseRdfFactory.connectDefaultModel( simpleStore );
		//Alt uriAlt = base.createAlt(uri);
		//uriAlt.
		//OntModel onto = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF, base );
		OntModel onto = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
		
		String[] arquivos = new String[]{
				userHome + "/Desktop/hbase/model/time.owl",
				userHome + "/Desktop/hbase/model/location.owl",
				userHome + "/Desktop/hbase/model/agents.owl",
				userHome + "/Desktop/hbase/model/context.owl"
		};
		
		String[] namespace = new String[] {
			"http://swe.unisinos.br/ont/time",
			"http://swe.unisinos.br/ont/location",
			"http://swe.unisinos.br/ont/agents",
			"http://swe.unisinos.br/ont/context"
		};
		
		OntDocumentManager dm = onto.getDocumentManager();
		for(int idx = 0; idx < arquivos.length; idx++) {
			dm.addAltEntry(namespace[idx], "file:" + arquivos[idx]);
		}
		
		onto.add(base);
		
		
		OntClass testCls = onto.getOntClass("http://swe.unisinos.br/ont/agents#Person");
		
		
		
		if(testCls == null) {
			
			
			for(int idx = 0; idx < arquivos.length; idx++) {
				onto.read("file:" + arquivos[idx]);
				/*
				File ontoFile = new File(arquivos[idx]);
				try {
					FileInputStream in = new FileInputStream(ontoFile);
					onto.read(in, namespace[idx]);
					in.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}
			onto.commit();
		}
		
		OntClass cls = onto.getOntClass("http://swe.unisinos.br/ont/agents#Person");
		OntProperty prop = onto.getOntProperty("http://swe.unisinos.br/ont/agents#facebookID");
		
		
		for(Iterator<Individual> i = onto.listIndividuals(cls); i.hasNext(); ) {
			Individual something = i.next();
			Statement st = something.getProperty(prop);
			if(st != null) { 
				System.out.println(something.getURI() + "//" + something.getLocalName() + "//" + st.getLong());
			}
		}
		
		
		
	}
	
	public static Store getStoreSimple()
	{
		Store ss = null;
		if( ss == null )
		{
			HBaseRdfConnection conn = HBaseRdfFactory.createConnection( userHome + "/Desktop/hbase/hbase/conf/hbase-site.xml", false ) ;
			StoreDesc desc = new StoreDesc( LayoutType.LayoutSimple, "ss" ) ;
			ss = new StoreSimple( conn, desc ) ;
			//ss.getTableFormatter().format() ;
		}
		else 
			ss.getTableFormatter().truncate();
		return ss ;
	}

}
