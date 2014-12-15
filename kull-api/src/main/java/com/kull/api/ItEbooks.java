/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.api;

import com.google.gson.Gson;
import com.kull.Netz;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author lin
 */
public class ItEbooks {
    
    public static String URL_BASE="http://it-ebooks-api.info/v1";
    private static Gson GSON=new Gson();
    
      public static SearchResult search(String query) throws IOException{
       return search(query, 1);
      }
    
    public static SearchResult search(String query,int page) throws IOException{
    
        String url=URL_BASE+"/search/"+query+"/page/"+page;
        String resStr= Netz.getString(url);
        return GSON.fromJson(resStr,SearchResult.class);
    }
    
     public static Book book(String id) throws IOException{
    
        String url=URL_BASE+"/book/"+id;
        String resStr= Netz.getString(url);
        return GSON.fromJson(resStr,Book.class);
    }
    
     public class SearchResult{
         protected List<Book> Books;
         protected String Error,Total;
         protected long Page;
         protected double Time;

        public List<Book> getBooks() {
            return Books;
        }

        public void setBooks(List<Book> Books) {
            this.Books = Books;
        }

        public String getError() {
            return Error;
        }

        public void setError(String Error) {
            this.Error = Error;
        }

        public double getTime() {
            return Time;
        }

        public void setTime(double Time) {
            this.Time = Time;
        }

        public String getTotal() {
            return Total;
        }

        public void setTotal(String Total) {
            this.Total = Total;
        }

        public long getPage() {
            return Page;
        }

        public void setPage(long Page) {
            this.Page = Page;
        }
     }
     
     public class Book{
       protected String ID, Title, SubTitle ,Description, Image,Author,ISBN,Publisher,Download;
       protected int Page,Year;

        @Override
        public String toString() {
            return MessageFormat.format("{0} \n[{1}]", this.Title,this.SubTitle);
        }

       
       
        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getSubTitle() {
            return SubTitle;
        }

        public void setSubTitle(String SubTitle) {
            this.SubTitle = SubTitle;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String Author) {
            this.Author = Author;
        }

        public String getISBN() {
            return ISBN;
        }

        public void setISBN(String ISBN) {
            this.ISBN = ISBN;
        }

        public String getPublisher() {
            return Publisher;
        }

        public void setPublisher(String Publisher) {
            this.Publisher = Publisher;
        }

        public String getDownload() {
            return Download;
        }

        public void setDownload(String Download) {
            this.Download = Download;
        }

        public int getPage() {
            return Page;
        }

        public void setPage(int Page) {
            this.Page = Page;
        }

        public int getYear() {
            return Year;
        }

        public void setYear(int Year) {
            this.Year = Year;
        }
       
     }
     
}
