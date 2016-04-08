package com.example.eray.customlistview;
import java.util.ArrayList;

/**
 * Created by Karayel on 09.02.2016.
 */
public class Favourites {

    ArrayList<Author> favAuthors;

    public Favourites(){

    }

    public void addToFavourites(Author newFavouriteAuthor) {
        favAuthors.add(newFavouriteAuthor);
    }

    public void removeFromFavourites(Author authorToDelete){
        favAuthors.remove(authorToDelete);
    }

    public void removeFromFavouritesWithAuthorId(int authorId){
        for(int i=0; i<this.favAuthors.size(); i++){
            if(this.favAuthors.get(i).getAuthorId() == authorId){
                this.favAuthors.remove(i);
            }
        }
    }

}
