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

    public void appendTrack(Track track) { tracks.add(track); }

    public List<Track> getTracks() { return tracks; }

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
                        new Duration(new int[] { 0, 0, 0 }), (x,i) -> { x.add(i); return x; });
        // Duration totalDuration = new Duration(new int[]{0,0,0});
        // Arrays.stream(tracks).forEach(x -> { totalDuration.addDuration(x); });
        // int[] totalDuration2 = Arrays.stream(tracks).map(Track::toInt).reduce(
        // new int[3], (total, duration) -> {
        // total[0] += duration[0];
        // total[1] += duration[1];
        // total[2] += duration[2];
        // return total;
        // });
        // return totalDuration;
        // totalDuration[1] += totalDuration[0] / 60;
        // totalDuration[0] %= 60;
        // totalDuration[2] += totalDuration[1] / 60;
        // totalDuration[1] %= 60;
        // return new Duration(totalDuration);
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
        if (reversed) { Collections.reverse(tracks); }
    }

    // Sorting ways

    // ====== Artist name sorting ======
    
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

    // public static final Comparator<Album> Artist = new Comparator<Album>() {
    //     @Override
    //     public int compare(Album a1, Album a2) {
    //         if (a1.artist.equals(a2.artist)) {
    //             return Integer.compare(a1.year, a2.year);
    //         }
    //         return a1.artist.compareTo(a2.artist);
    //     }
    // };

    // public static final Comparator<Album> ArtistLength = new Comparator<Album>() {
    //     @Override
    //     public int compare(Album a1, Album a2) {
    //         return Integer.compare(a1.artist.length(), a2.artist.length());
    //     }
    // };

    // // ====== Artist name sorting ======

    // // ====== Year sorting ======

    // public static final Comparator<Album> Year = new Comparator<Album>() {
    //     @Override
    //     public int compare(Album a1, Album a2) {
    //         return Integer.compare(a1.year, a2.year);
    //     }
    // };

    // // ====== Year sorting ======

    // // ====== Album sorting ======

    // public static final Comparator<Album> AlbumName = new Comparator<Album>() {
    //     @Override
    //     public int compare(Album a1, Album a2) {
    //         return a1.albumName.compareTo(a2.albumName);
    //     }
    // };

    // public static final Comparator<Album> AlbumNameLength = new Comparator<Album>() {
    //     @Override
    //     public int compare(Album a1, Album a2) {
    //         return Integer.compare(a1.albumName.length(), a2.albumName.length());
    //     }
    // };

    // public static final Comparator<Album> AlbumDuration = new Comparator<Album>() {
    //     @Override
    //     public int compare(Album a1, Album a2) {
    //         return a1.getAlbumDuration().compareTo(a2.getAlbumDuration());
    //     }
    // };

    // ====== Album sorting ======

}
