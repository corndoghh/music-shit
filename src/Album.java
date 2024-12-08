import java.util.Arrays;

public class Album {

    private final String artist;
    private final String albumName;
    private final int year;
    private Track[] tracks;

    public Album(String artist, String albumName, int year, Track[] tracks) {
        this.artist = artist;
        this.albumName = albumName;
        this.year = year;
        this.tracks = tracks;
    }

    public Album(String artist, String albumName, int year) {
        this.artist = artist;
        this.albumName = albumName;
        this.year = year;
        this.tracks = new Track[]{};
    }

    public void appendTrack(Track track) {
        Track[] newTracks = Arrays.copyOf(tracks, tracks.length+1);
        newTracks[tracks.length] = track;
        tracks = newTracks;
    }

    public Track[] getTracks() { return tracks; }

    public String getArtist() { return this.artist; }
    public int getYear() { return this.year; }
    public String getAlbumName () { return this.albumName; }


}
