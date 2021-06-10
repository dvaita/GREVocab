package com.example.grevocab;

import java.util.ArrayList;

public class UserRecord {

    ArrayList<Integer> bookmarks;
    ArrayList<Integer> completedWords;

    public UserRecord() {
        bookmarks=new ArrayList<>();
        completedWords=new ArrayList<>();
    }

    public ArrayList<Integer> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(ArrayList<Integer> bookmarks) {
        this.bookmarks = bookmarks;
    }
}
