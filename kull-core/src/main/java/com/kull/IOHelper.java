package com.kull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class IOHelper {
public static String TARGET_PATH_MAVEN="target/classes";


public static enum FileType{
	jpg("FFD8FF"), //JPEG (jpg)

	png("E47"), //PNG (png)

	gif(""), //GIF (gif)

	tif("A00"), //TIFF (tif)

	bmp("424D"), //Windows Bitmap (bmp)

	dwg(""), //CAD (dwg)

	html("D6C3E"), //HTML (html)

	rtf("7B5C"), //Rich Text Format (rtf)

	xml("3C3F786D6C"),

	zip("504B0304"),

	rar(""),

	psd(""), //Photoshop (psd)

	eml("CDA"), //Email [thorough only] (eml)

	dbx("CFAD12FEC5FD746F"), //Outlook Express (dbx)

	pst("E"), //Outlook (pst)

	xls("D0CF11E0"), //MS Word

	doc("D0CF11E0"), //MS Excel 留意：word 和 excel的文件头一样

	mdb("EA"), //MS Access (mdb)

	wpd("FF"), //WordPerfect (wpd)

	eps("DF6265"),

	ps("DF6265"),

	pdf("D312E"), //Adobe Acrobat (pdf)

	qdf("AC9EBD8F"), //Quicken (qdf)

	pwl("E"), //Windows Password (pwl)

	wav(""), //Wave (wav)

	avi(""),

	ram("2E7261FD"), //Real Audio (ram)

	rm("2E524D46"), //Real Media (rm)

	mpg("000001BA"), //

	mov("6D6F6F76"), //Quicktime (mov)

	asf("3026B2758E66CF11"), //Windows Media (asf)

	mid("4D"); //MIDI (mid)
	

	public final String hex;
	
	FileType(String hex){
		this.hex=hex;
	}

	
	
}

   public final static FileType[] TextFileTypes=new FileType[]{FileType.html};


	public static String classPath(){
	   return ClassLoader.getSystemClassLoader().getResource("").getPath();
	}
	
	
	
	public static String projectPath(String targetPath){
	   String clsPath=classPath();
	   String projectPath=clsPath.substring(0,clsPath.lastIndexOf(targetPath));
	   return projectPath;
	}
	
	
	
	
	
	public static String pathFormat(Class c){
		String cls=c.getName();
		cls=cls.substring(0,cls.lastIndexOf("."));
	   return cls.replace(".", "/");	
	}
	
	
	
	public static String ClassPath(Class c){
		return MessageFormat.format("{0}/{1}",
		  trimPath(classPath())
		  ,pathFormat(c)
		);
	}
	
	public static URL selectParentDir(URL url) throws MalformedURLException{
		return selectParentDir(url,1);
	}
	
	public static URL selectParentDir(URL url,int level) throws MalformedURLException{
		if(url==null)return url;
		String parentPath=url.getPath();
		for(int i=0;i<level;i++){
			File f=new File(parentPath);
			parentPath=f.getParent();
		}
		URL parentUrl=new URL(url.getProtocol(), url.getHost(), parentPath);
		return parentUrl;
	}
	
	public static URL selectParentDir(URL url,String parent) throws MalformedURLException{
		if(url==null)return url;
		String parentPath=url.getPath();
		File f=new File(parentPath);
		while(f.getParentFile()!=null){
			f=f.getParentFile();
			if(f.getName().equals(parent))
				break;
		}	
		parentPath=f.getPath();
		URL parentUrl=new URL(url.getProtocol(), url.getHost(), parentPath);
		return parentUrl;
	}
	
	public static boolean saveFile(String path,byte[] bytes) throws IOException{
		File file=new File(path);
		FileOutputStream fos=null;
		if(!file.exists()){
			file.createNewFile();
		}else if(!file.isFile()){
			return false;
		}
	    fos=new FileOutputStream(file);
		fos.write(bytes);
		fos.flush();
		fos.close();
        return true;
	}
	
	
	
	
	public static byte[]  toBytes(File file) throws IOException{
		if(!file.exists()||!file.isFile())	throw new IOException();
		FileInputStream fis=new FileInputStream(file);
		byte[] bytes=new byte[fis.available()];
		fis.read(bytes);
		fis.close();
		return bytes;
	}
	
	
	public static String getWebClassesPath() {
		   String path = ClassLoader.class.getProtectionDomain().getCodeSource()
		     .getLocation().getPath();
		   return path;
		  
		}

   public static String getWebInfPath() throws IllegalAccessException{
		   String path = getWebClassesPath();
		   if (path.indexOf("WEB-INF") > 0) {
		    path = path.substring(0, path.indexOf("WEB-INF")+8);
		   } else {
		    throw new IllegalAccessException("path error");
		   }
		   return path;
		}

  public static String getWebRoot() throws IllegalAccessException{
		   String path = getWebClassesPath();
		   if (path.indexOf("WEB-INF") > 0) {
		    path = path.substring(0, path.indexOf("WEB-INF/classes"));
		   } else {
		    throw new IllegalAccessException("path error");
		   }
		   return path;
		}

	
	public static String trimPath(String filePath){
		return StringHelper.trim(filePath, "/","\\");
	}
	
	public static void createZip(File zip, String... sourceFiles) throws IOException {
	   if(!zip.exists()){
		   zip.createNewFile();
	   }
	   if(!zip.isFile()){
		   throw new IOException(zip.getName() +" is not a file!");
	   }
	   FileOutputStream fileOutputStream=new FileOutputStream(zip);
	   createZip(fileOutputStream, sourceFiles);
	}
	
	public static void createZip(OutputStream os, String... sourceFiles) throws IOException {

	     
	           //os = new FileOutputStream(zipFile);

	           BufferedOutputStream bos = new BufferedOutputStream(os);

	           ZipOutputStream zos = new ZipOutputStream(bos);

	 
               for(String sourceFile : sourceFiles){
	           File file = new File(sourceFile);

		           String basePath = null ;
	
		           if (file.exists()&&file.isDirectory()) {
	
		              basePath = file.getPath();
	
		           } else if(file.isFile()) {
		              basePath = file.getParent();
	
		           }else {
		        	   throw new IOException(sourceFile+ " is not exist");
		           }
		 
		           zipFile (file, basePath, zos);
               }
	 

	           zos.closeEntry();

	           zos.close();

	       }

	 

	    

	 

	   

	    private static void zipFile(File source, String basePath,

	           ZipOutputStream zos) {

	       File[] files = new File[0];

	 

	       if (source.isDirectory()) {

	           files = source.listFiles();

	       } else {

	           files = new File[1];

	           files[0] = source;

	       }

	 

	       String pathName;

	       byte [] buf = new byte [1024];

	       int length = 0;

	       try {

	           for (File file : files) {

	              if (file.isDirectory()) {

	                  pathName = file.getPath().substring(basePath.length() + 1)

	                         + "/" ;

	                  zos.putNextEntry( new ZipEntry(pathName));

	                  zipFile (file, basePath, zos);

	              } else {

	                  pathName = file.getPath().substring(basePath.length() + 1);

	                  InputStream is = new FileInputStream(file);

	                  BufferedInputStream bis = new BufferedInputStream(is);

	                  zos.putNextEntry( new ZipEntry(pathName));

	                  while ((length = bis.read(buf)) > 0) {

	                     zos.write(buf, 0, length);

	                  }

	                  is.close();

	              }

	           }

	       } catch (Exception e) {

	           // TODO Auto-generated catch block

	           e.printStackTrace();

	       }

	 

	    }

	 

	   

	    public static void UnZip(String zipfile, String destDir) {

	 

	       destDir = destDir.endsWith( "\\" ) ? destDir : destDir + "\\" ;

	       byte b[] = new byte [1024];

	       int length;

	 

	       ZipFile zipFile;

	       try {

	           zipFile = new ZipFile( new File(zipfile));

	           Enumeration enumeration = zipFile.entries();

	           ZipEntry zipEntry = null ;

	 

	           while (enumeration.hasMoreElements()) {

	              zipEntry = (ZipEntry) enumeration.nextElement();

	              File loadFile = new File(destDir + zipEntry.getName());

	 

	              if (zipEntry.isDirectory()) {

	                  // 杩欐閮藉彲浠ヤ笉瑕侊紝鍥犱负姣忔閮借矊浼间粠鏈?簳灞傚紑濮嬮亶鍘嗙殑

	                  loadFile.mkdirs();

	              } else {

	                  if (!loadFile.getParentFile().exists())

	                     loadFile.getParentFile().mkdirs();

	 

	                  OutputStream outputStream = new FileOutputStream(loadFile);

	                  InputStream inputStream = zipFile.getInputStream(zipEntry);

	 

	                  while ((length = inputStream.read(b)) > 0)

	                     outputStream.write(b, 0, length);

	 

	              }

	           }

	           System. out .println( " 鏂囦欢瑙ｅ帇鎴愬姛 " );

	       } catch (IOException e) {

	           // TODO Auto-generated catch block

	           e.printStackTrace();

	       }

	 

	    }
	    
	    
	    public final static String hexString(byte[] b)     
	    {     
	        StringBuilder stringBuilder = new StringBuilder();     
	        if (b == null || b.length <= 0)     
	        {     
	            return null;     
	        }     
	        for (int i = 0; i < b.length; i++)     
	        {     
	            int v = b[i] & 0xFF;     
	            String hv = Integer.toHexString(v);     
	            if (hv.length() < 2)     
	            {     
	                stringBuilder.append(0);     
	            }     
	            stringBuilder.append(hv);     
	        }     
	        return stringBuilder.toString();     
	    }
	    
	    public final static FileType fileType(File file) throws IOException{
	    	return fileType(toBytes(file)); 
	    }
	    
	    public final static FileType fileType(byte[] b)

	    {

	    String filetypeHex = String.valueOf(hexString(b));
	   

	    for(FileType fileType : FileType.values()){
	    if (filetypeHex.toUpperCase().startsWith(fileType.hex)) return fileType;
	    }
	    return null;

	    }
}
