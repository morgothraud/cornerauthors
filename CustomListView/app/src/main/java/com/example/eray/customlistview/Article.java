package com.example.eray.customlistview;

import java.util.Date;
/*
 * Created by Karayel on 09.02.2016.
 */
public class Article {

    private int id;
    private String header;
    private String content;
    private String date;
    private Author author;
    private String url;

    public Article(){


    }

    public Article(int id , String header, String content, String date, Author author){

    }

    public Article(String header, String content, Date date, Author author){

    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String newUrl){
        this.url = newUrl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
