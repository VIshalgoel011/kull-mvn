package com.kull.orm.mybatis;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.text.MaskFormatter;

import org.dom4j.Branch;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.QName;
import org.dom4j.Visitor;
import org.dom4j.XPath;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMDocumentType;
import org.xml.sax.EntityResolver;

public class MapperDocument extends DOMDocument {

	private static DocumentType documentType;
	
	public static DocumentType getDocumentType(){
		if(documentType!=null)return documentType;
    	documentType=new DOMDocumentType();
    	documentType.setName("mapper");
    	documentType.setPublicID("-//mybatis.org//DTD Mapper 3.0//EN");
    	documentType.setSystemID("http://mybatis.org/dtd/mybatis-3-mapper.dtd");
        return documentType;
	}
	
    private MapperTemplate mapperTemplate;
 	private Element elMapper;
	private Element elSqlCache;
	private Element elSqlTable;
	private Element elSqlPk;
	
	
	public MapperDocument(MapperTemplate mapperTemplate){
	 	this.mapperTemplate=mapperTemplate;
	}
	
	private void init(){
       this.setXMLEncoding("UTF-8");
       this.setDocType(documentType);
       
	}
	
}
