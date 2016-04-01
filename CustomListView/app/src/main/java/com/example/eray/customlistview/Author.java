package com.example.eray.customlistview;
import java.util.ArrayList;

/**
 * Created by Eray Asan on 16.02.2016.
 */
public class Author {

    private String name;
    private NewsPaper newsPaper;
    private ArrayList<Article> articles;
    private int authorId;

    public Author(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewsPaper getNewsPaper() {
        return newsPaper;
    }

    public void setNewsPaper(NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }


    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
