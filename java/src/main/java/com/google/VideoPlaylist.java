package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
public class VideoPlaylist {
    String name;
    ArrayList<Video> playList=new ArrayList<>();
    public VideoPlaylist(String name){
        this.name=name;

    }
    public String getName(){
        return name;
    }
    public void addVideo(Video vid){
        playList.add(vid);
    }
    public int consists(Video v){
        if (playList.contains(v))
            return 1;
        return 0;
    }
    public ArrayList<Video> returnList(){
        return playList;
    }
    public int checker(Video v){
        if(playList.contains(v))
            return 1;
        return 0;
    }
    public void clear(){
        int i=0;
        while(playList.size()>=1){
            playList.remove(i);
            i++;
        }
    }
    public void remove(Video v){
        playList.remove(v);
    }





}
