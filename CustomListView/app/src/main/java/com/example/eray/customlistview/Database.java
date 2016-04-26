package com.example.eray.customlistview;

/**
 * Created by Eray Asan on 16.02.2016.
 */


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CornerWritersDB";

    // Articles table name

    private static final String TABLE_SAVED = "articles";

    // Favourites table name
    private static final String TABLE_FAVOURITES = "favourites";

    // Articles Table Columns names
    private static final String PRIMARY_KEY_ARTICLE_ID = "id";
    private static final String KEY_HEADER = "header";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE = "authorImage";
    private static final String KEY_NEWSPAPER = "newspaper";

    // Favourites Table Columns names
    private static final String PRIMARY_KEY_AUTHOR_ID = "id";
    private static final String KEY_AUTOR_NAME = "author_name";
    private static final String KEY_IS_FAVOURITED = "is_favourited";
    private static final String KEY__AUTOR_IMAGE = "autorImage";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SAVED + "("
                + PRIMARY_KEY_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_HEADER + " TEXT,"
                + KEY_CONTENT + " TEXT," + KEY_AUTHOR + " TEXT," + KEY_DATE + " TEXT,"+ KEY_IMAGE + " TEXT," + KEY_NEWSPAPER + " TEXT)";
        String CREATE_FAVOURITES_TABLE = "CREATE TABLE " + TABLE_FAVOURITES + "(" + PRIMARY_KEY_AUTHOR_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_AUTOR_NAME + " TEXT," + KEY__AUTOR_IMAGE + " TEXT," + KEY_IS_FAVOURITED + " INTEGER)";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_FAVOURITES_TABLE);
        Log.d("DATABASEONCREATE","created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);

        // Create tables again
        onCreate(db);
    }


    public String getTableAsString(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Log.d("t", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        Log.d("TEST",tableString);
        return tableString;
    }

    void column() {
        SQLiteDatabase mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_SAVED, null, null, null, null, null, null,null);
        String[] columnNames = dbCursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {

            Log.d("COL",   columnNames[i].toString());

        }
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Save article
  public void addArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
     //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED);
        ContentValues values = new ContentValues();

        if(!checkIFRowExists(article)) {
        Log.d("SAVED","not exist");
      // AUTHOR NAME,IMAGE,NEWSPAPER ARTICLE, HEADER,CONTENT,DATE
        values.put(KEY_HEADER, article.getHeader().replace("'","")); // Contact Name
        values.put(KEY_CONTENT, article.getContent()); // Contact Phone
        values.put(KEY_AUTHOR, article.getAuthor().getName()); // Contact Name
        Log.d("Databaseİsim",article.getAuthor().getName());
        values.put(KEY_DATE, article.getDate().toString()); // Contact Phone
        values.put(KEY_IMAGE, article.getAuthor().getImageUrl()); // Contact Phone
        values.put(KEY_NEWSPAPER, article.getAuthor().getNewsPaper().name); // Contact Phone
        column();
        // Inserting Row
        db.insert(TABLE_SAVED, null, values);
        }
        db.close(); // Closing database connection
        Log.d("DATABASE","ADDED" + article.getHeader());
    }

    // Getting single article
   public Article getArticle(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SAVED, new String[] { PRIMARY_KEY_ARTICLE_ID,
                        KEY_HEADER, KEY_CONTENT, KEY_DATE, KEY_AUTHOR }, PRIMARY_KEY_ARTICLE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Author author = new Author();
        author.setName(cursor.getString(4));
        Article article = new Article(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), author);

       // cursor.getString(0) -> id
       // cursor.getString(1) -> header
       // cursor.getString(2) -> content
       // cursor.getString(3) -> date
       // cursor.getString(4) -> author
        // return article
        return article;
    }

    // Getting All Contacts
    public List<Article> getAllArticles() {
        List<Article> contactList = new ArrayList<Article>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SAVED;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // AUTHOR IMAGE,NEWSPAPER

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Author author;
            Article article;
            do {
                article = new Article();
                author = new Author();
                article.setId(Integer.parseInt(cursor.getString(0)));
                article.setHeader(cursor.getString(1));
                article.setContent(cursor.getString(2));
                article.setDate(cursor.getString(4));
                author.setName(cursor.getString(3));
                author.setImageUrl(cursor.getString(5));
                author.setNewsPaper(new NewsPaper(cursor.getString(6)));
                article.setAuthor(author);

                // Adding contact to list
                contactList.add(article);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single article
    public int updateArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HEADER, article.getHeader());
        values.put(KEY_CONTENT, article.getContent());
        values.put(KEY_DATE,article.getDate());
        values.put(KEY_AUTHOR, article.getAuthor().getName());

        // updating row
        return db.update(TABLE_SAVED, values, PRIMARY_KEY_ARTICLE_ID + " = ?",
                new String[] { String.valueOf(article.getId())});
    }

    // Deleting single article
    public void deleteArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAVED, PRIMARY_KEY_ARTICLE_ID + " = ?",
                new String[]{String.valueOf(article.getId())});
        db.close();
    }

    // Deleting single article with id
    public void deleteArticleWithId(int articleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAVED, PRIMARY_KEY_ARTICLE_ID + " = ?",
                new String[]{String.valueOf(articleId)});
        Log.d("DATABASE DELETE", "" + articleId+ " deleted successfully.");
        db.close();
    }

    // Getting article Count
    public int getArticlesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SAVED;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Adding new favourite
    public void addFavourite(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(!checkIFRowExistsInFavorites(article)) {
        values.put(KEY_AUTOR_NAME, article.getAuthor().getName()); // Contact Name
        Log.d("Databaseİsim",article.getAuthor().getName());
        values.put(KEY__AUTOR_IMAGE, article.getAuthor().getImageUrl()); // Contact Phone
        values.put(KEY_IS_FAVOURITED,1);
        // Inserting Row
        db.insert(TABLE_FAVOURITES, null, values);
        Log.d("FAVORITE","ADDED" + article.getAuthor().getName());
        }
        db.close(); // Closing database connection
    }

    // Getting single favourite
    public Article getFavourite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SAVED, new String[] { PRIMARY_KEY_ARTICLE_ID,
                        KEY_HEADER, KEY_CONTENT, KEY_DATE, KEY_AUTHOR }, PRIMARY_KEY_ARTICLE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Author author = new Author();
        author.setName(cursor.getString(4));
        Article article = new Article(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), author);

        // cursor.getString(0) -> id
        // cursor.getString(1) -> header
        // cursor.getString(2) -> content
        // cursor.getString(3) -> date
        // cursor.getString(4) -> author
        // return article
        return article;
    }

    public boolean checkIFRowExists(Article a) {
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "SELECT * FROM articles WHERE header='"+a.getHeader().replace("'","") +"' AND author='"+a.getAuthor().getName()+"' AND date='"+a.getDate()+"'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkIFRowExistsInFavorites(Article a) {
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "SELECT * FROM favourites WHERE author_name='"+a.getAuthor().getName()+"'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    // Getting All Favourites
    public List<Article> getAllFavourites() {
        List<Article> contactList = new ArrayList<Article>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Author author;
            Article article;
            do {
                article = new Article();
                author = new Author();
                article.setId(Integer.parseInt(cursor.getString(0)));
               // article.setHeader(cursor.getString(1));
             //   article.setContent(cursor.getString(2));
              //  article.setDate(cursor.getString(4));
                author.setName(cursor.getString(1));
                author.setImageUrl(cursor.getString(2));
               // author.setNewsPaper(new NewsPaper(cursor.getString(6)));
                article.setAuthor(author);
                // Adding contact to list
                contactList.add(article);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single favourite
    public int updateFavourite(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HEADER, article.getHeader());
        values.put(KEY_CONTENT, article.getContent());
        values.put(KEY_DATE,article.getDate());
        values.put(KEY_AUTHOR, article.getAuthor().getName());

        // updating row
        return db.update(TABLE_SAVED, values, PRIMARY_KEY_ARTICLE_ID + " = ?",
                new String[] { String.valueOf(article.getId())});
    }

    public void deleteFavourite(Author author){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAVED, PRIMARY_KEY_AUTHOR_ID + " = ?",
                new String[]{String.valueOf(author.getAuthorId())});
        db.close();
    }

    // Deleting single favourite with id
    public void deleteFavouriteWithId(int favouriteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVOURITES, PRIMARY_KEY_AUTHOR_ID + " = ?",
                new String[]{String.valueOf(favouriteId)});
        db.close();
    }

    // Getting favourites Count
    public int getFavouritesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

}
