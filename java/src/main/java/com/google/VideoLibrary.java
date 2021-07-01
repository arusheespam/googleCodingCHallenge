package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;


  VideoLibrary() {
    this.videos = new HashMap<>();
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  List<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }
  Video getVid(String title){
    List<Video> vids=getVideos();
   List<String> str=new ArrayList<String>();
   for(Video v:vids){
     if(v.getTitle().equals(title))
        return v;
   }
   return new Video("null","null",str);
  }
  List<Video> refine(String word){
    List<Video> vids=getVideos();
    List<Video> finalList=new ArrayList<>();
    for(Video v:vids){
      if(v.getTitle().toLowerCase().contains(word.toLowerCase())){
        finalList.add(v);
      }
    }
    return finalList;
  }
  List<Video> refineTags(String word){
    List<Video> vids=getVideos();
    List<Video> finalList=new ArrayList<>();
    for(Video v:vids){
      if(v.getTags().contains(word.toLowerCase())){
        finalList.add(v);
      }
    }
    return finalList;
  }
}
