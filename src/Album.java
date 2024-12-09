import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Album {

    private final String artist;
    private final String albumName;
    private final int year;
    private List<Track> tracks;

    public Album(String artist, String albumName, int year, List<Track> tracks) {
        this.artist = artist;
        this.albumName = albumName != null ? albumName.stripTrailing() : null;
        this.year = year;
        this.tracks = new ArrayList<>(tracks);
    }

    public Album(String artist, String albumName, int year) {
        this(artist, albumName, year, new ArrayList<>());
    }

    public void appendTrack(Track track) {
        tracks.add(track);
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public String getArtist() {
        return this.artist;
    }

    public int getYear() {
        return this.year;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public Duration getAlbumDuration() {
        return tracks.stream()
                .map(x -> (Duration) x)
                .reduce(
                        new Duration(), (x, i) -> {
                            x.add(i);
                            return x;
                        });
    }

    @Override
    public String toString() {
        return "Artist: " + this.artist + ", Album Title: " + this.albumName + ", Year of Release: "
                + String.valueOf(this.year) + ", Album Duration: " + getAlbumDuration().toString();
    }

    // Track sorting
    public void sort() {
        sort(Track.TRACK_DURATION, false);
    }

    public void sort(Comparator<Track> comparator) {
        sort(comparator, false);
    }

    public void sort(Comparator<Track> comparator, Boolean reversed) {
        tracks.sort(comparator);
        if (reversed) {
            Collections.reverse(tracks);
        }
    }

    // Sorting ways

    public static final Comparator<Album> ARTIST = Comparator
            .comparing(Album::getArtist)
            .thenComparingInt(Album::getYear);

    public static final Comparator<Album> ARTIST_LENGTH = Comparator
            .comparingInt(album -> album.getArtist().length());

    public static final Comparator<Album> YEAR = Comparator
            .comparingInt(Album::getYear);

    public static final Comparator<Album> ALBUM_NAME = Comparator
            .comparing(Album::getAlbumName);

    public static final Comparator<Album> ALBUM_NAME_LENGTH = Comparator
            .comparingInt(album -> album.getAlbumName().length());

    public static final Comparator<Album> ALBUM_DURATION = Comparator
            .comparing(Album::getAlbumDuration);

}
