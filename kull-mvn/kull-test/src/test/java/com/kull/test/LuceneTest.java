package com.kull.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;



import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.kull.lucene.FileDocument;
import com.kull.lucene.FileDocument.FieldName;
import com.kull.lucene.FsRamDirectory;
import com.kull.lucene.TextFileDocument;

public class LuceneTest {

	
	FsRamDirectory fsRamDirectory;;
	IndexWriter indexWriter;
	StandardAnalyzer analyzer ;
	String testfilePath="K:/ws-sts/kull-mvn/kull-core/src/test/resources/datasource/readme.txt";
	String testDirPath="I:/百度云/doc";

	public LuceneTest() throws Exception {
		analyzer=new StandardAnalyzer(Version.LUCENE_36);
		fsRamDirectory=new FsRamDirectory("k:/cn-index/",analyzer);
		
	}

	//@After
	public void tearDown() throws Exception {
		fsRamDirectory.ramToFs();
		fsRamDirectory.close();
		fsRamDirectory=null;
	}

	//@Test
	public void addDoc() throws Exception {
	    //indexWriter=fsRamDirectory.ramIndexWriter;
		//TextFileDocument doc=new TextFileDocument(testfilePath);
	    //indexWriter.addDocument(doc);
	    //indexWriter.commit();
	    //indexWriter.close();
		fsRamDirectory.addFileDocuments(testDirPath, true);
	}
	
	//@Test
	public void search() throws Exception{
		IndexSearcher searcher=fsRamDirectory.createRamIndexSearcher();
		TopDocs topDocs=fsRamDirectory.search("software", 1, TextFileDocument.FieldName.context.name());
	    
		for(ScoreDoc sdoc : topDocs.scoreDocs){
	       	Document doc= searcher.doc(sdoc.doc);
	       	System.out.println(doc.get(FileDocument.FieldName.path.name()));
	    }
	}

}
