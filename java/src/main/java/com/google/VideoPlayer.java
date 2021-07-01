package com.google;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class VideoPlayer {
  String playStatus="notPlaying";
  ArrayList<VideoPlaylist> collection= new ArrayList<>();
  private final VideoLibrary videoLibrary;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videosList=videoLibrary.getVideos();
    videosList.sort(Comparator.comparing(Video::getTitle));
    for(Video v:videosList){
      System.out.print("\t");
      getVideoFormatted(v);
      System.out.println("");
    }
  }
  public void getVideoFormatted(Video v){
    String res =v.getTitle()+" ";
    res+="("+v.getVideoId()+") ";
    res+=v.getTags().toString().replaceAll(",","");
    System.out.print(res);
  }

  public void playVideo(String videoId) {
    String video;
    try{
    video=videoLibrary.getVideo(videoId).getTitle();}
    catch (Exception e){
      video="null";
    }
    if(video.equals("null")){
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    if(!playStatus.equals("notPlaying"))
      stopVideo();
    playStatus=video;
    System.out.println("Playing video: "+video);
  }

  public void stopVideo() {
    if(!playStatus.equals("notPlaying")){
      System.out.println("Stopping video: "+playStatus);
      playStatus="notPlaying";
    }
    else{
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }
  private int randomNum(List<Video> list){
    Random rand = new Random();
    return rand.nextInt((list.size() - 1) + 1);
  }

  public void playRandomVideo() {
    List<Video> videos=videoLibrary.getVideos();
    int num=randomNum(videos);
    playVideo(videos.get(num).getVideoId());
  }

  public void pauseVideo() {
    if(playStatus.equals("notPlaying")){
      System.out.println("Cannot pause video: No video is currently playing");
      return;
    }
    if(!playStatus.contains("paused")){
      System.out.println("Pausing video: "+ playStatus);
      playStatus=playStatus+"paused";
    }
    else{
      System.out.println("Video already paused: "+playStatus.replace("paused",""));
    }

  }

  public void continueVideo() {
    if(playStatus.equals("notPlaying")){
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }
    if(!playStatus.contains("paused")){
      System.out.println("Cannot continue video: Video is not paused");
      //playStatus=playStatus+"paused";
    }
    else{
      System.out.println("Continuing video: "+playStatus.replace("paused",""));
    }

  }

  public void showPlaying() {
    if(playStatus.equals("notPlaying")){
      System.out.println("No video is currently playing");
    }
    else if(playStatus.contains("paused")){
      Video vid=videoLibrary.getVid(playStatus.replaceAll("paused",""));
      System.out.print("Currently playing: ");
      getVideoFormatted(vid);
      System.out.println(" - PAUSED");
    }
    else{
      Video vid=videoLibrary.getVid(playStatus);
      System.out.print("Currently playing: ");
      getVideoFormatted(vid);
      //System.out.println();
    }
  }
  //checks whether the playList exists(Case sensitive)
  private int contains(String playlistName){
    for(VideoPlaylist v:collection){
      if(v.getName().toLowerCase().equals(playlistName.toLowerCase()))
        return 1;
    }
    return 0;
  }

  public void createPlaylist(String playlistName) {
    VideoPlaylist playlist=new VideoPlaylist(playlistName);
    if(contains(playlistName)==0) {
      collection.add(playlist);
      System.out.println("Successfully created new playlist: " + playlistName);
    }
    else{
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
  }
  private VideoPlaylist checker(String playlistName){
    for(VideoPlaylist v:collection){
      if(v.getName().toLowerCase().equals(playlistName.toLowerCase()))
        return v;
    }
    return new VideoPlaylist("null");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
   VideoPlaylist list=checker(playlistName);
   Video vid=videoLibrary.getVideo(videoId);
   // if(!collection.contains(list)){
    //  System.out.println("Cannot add video to "+ playlistName+": Playlist does not exist");
    //  return;
   // }
    if(list.getName().equals("null")){
      System.out.println("Cannot add video to "+ playlistName+": Playlist does not exist");
        return;
    }
    if(list.consists(vid)==1)
     System.out.println("Cannot add video to "+ playlistName +": Video already added");
    else{
     if(vid!=null){
     list.addVideo(vid);
     System.out.println("Added video to "+playlistName+": "+vid.getTitle());}
     else
       System.out.println("Cannot add video to "+ playlistName +": Video does not exists");
   }
  }

  public void showAllPlaylists() {
    if(collection.size()==0){
      System.out.println("No playlists exist yet");
    }
    else{
      System.out.println("Showing all playlists:");
      //System.out.println(collection.size());
      for(int i=collection.size()-1;i>=0;i--){//VideoPlaylist playlists:collection){
        System.out.println("\t"+collection.get(i).getName());
      }
    }
  }

  public void showPlaylist(String playlistName) {
    VideoPlaylist playlist=checker(playlistName);
    if(playlist.getName().equals("null")){
      System.out.println("Cannot show playlist "+ playlistName+": Playlist does not exist");
      return;
    }
    if (playlist.returnList().size()==0) {
      System.out.println("Showing playlist: " + playlistName);
      System.out.println("\t No videos here yet");
    }
    else{
      System.out.println("Showing playlist: " + playlistName);
      for(Video l:playlist.returnList()){
        //System.out.print("\t");
        getVideoFormatted(l);
        System.out.println("");

      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    VideoPlaylist playlist=checker(playlistName);
    if(playlist.getName().equals("null")){
      System.out.println("Cannot remove video from "+ playlistName+": Playlist does not exist");
      return;
    }
    Video vid=videoLibrary.getVideo(videoId);
    if(vid==null)
      System.out.println("Cannot remove video from "+playlistName+": "+"Video does not exist");
    else
      if(playlist.checker(vid)!=0) {
        playlist.remove(vid);
        System.out.println("Removed video from " + playlistName + ": " + vid.getTitle());
      }
      else
      System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
      //else
       //
  }

  public void clearPlaylist(String playlistName) {
    VideoPlaylist playlist=checker(playlistName);
    if(playlist.getName().equals("null")){
      System.out.println("Cannot clear playlist "+ playlistName+": Playlist does not exist");
      return;
    }
    playlist.clear();
    System.out.println("Successfully removed all videos from "+playlistName);
  }

  public void deletePlaylist(String playlistName) {
    VideoPlaylist playlist=checker(playlistName);
    if(playlist.getName().equals("null")){
      System.out.println("Cannot delete playlist "+ playlistName+": Playlist does not exist");
      return;
    }
    collection.remove(playlist);
    System.out.println("Deleted playlist: "+playlistName);
  }
  public void getInput(List<Video> list) throws IOException {
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no."); //\n
      var in = input.nextInt();
      if (in < list.size() + 1) {
        playVideo(list.get(in-1).getVideoId());
      }
    }
    catch (Exception e){
      System.out.print("");
    }
  }

  public void searchVideos(String searchTerm)  {
    List<Video> list=videoLibrary.refine(searchTerm);
    list.sort(Comparator.comparing(Video::getTitle));
    if(list.size()==0){
      System.out.println("No search results for "+searchTerm);
      return;
    }
    System.out.println("Here are the results for "+searchTerm+":");
    for(int i=0;i<list.size();i++){
      System.out.print((i+1)+") ");
      getVideoFormatted(list.get(i));
      System.out.println("");
    }
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no."); //\n
      var in = input.nextInt();
      if (in < list.size() + 1) {
        playVideo(list.get(in-1).getVideoId());
      }
    }
    catch (Exception e){
      System.out.print("");
    }
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> list=videoLibrary.refineTags(videoTag);

    if(list.size()==0){
      System.out.println("No search results for "+videoTag);
      return;
    }
    list.sort(Comparator.comparing(Video::getTitle));
    System.out.println("Here are the results for "+videoTag+":");
    for(int i=0;i<list.size();i++){
      System.out.print((i+1)+") ");
      getVideoFormatted(list.get(i));
      System.out.println("");
    }
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no."); //\n
    try {
      Scanner input = new Scanner(System.in);
      var in = input.nextInt();
      if (in < list.size() + 1) {
        playVideo(list.get(in-1).getVideoId());
      }
    }
    catch (Exception e){
      System.out.print("");
    }

  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}