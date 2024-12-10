import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumCollection {

    private List<Album> albums;

    public AlbumCollection(List<Album> albums) {
        this.albums = albums;
    }

    public AlbumCollection() {
        this(new ArrayList<>());
    }

 

    public void appendAlbum(Album album) {
        albums.add(album);
    }

    public List<Album> getAlbums() {
        return albums;
    }

    // ====== Album Sorting ======

    public void sort() {
        sort(Album.ARTIST, false);
    }

    public void sort(Comparator<Album> comparator) {
        sort(comparator, false);
    }

    public void sort(Comparator<Album> comparator, Boolean reversed) {
        reversed = (comparator == Album.ALBUM_NAME_LENGTH || comparator == Album.ARTIST_LENGTH) ? !reversed : reversed;
        albums.sort(comparator);
        if (reversed) {
            Collections.reverse(albums);
        }
    }

    // ====== Album Sorting ======

    public Album getAlbumByIdentifier(String identifier) {
        return getAlbumsByIdentifier(identifier).getAlbums().get(0);
    }

    public AlbumCollection getAlbumsByIdentifier(String identifier) {
        return new AlbumCollection(albums
                .stream()
                .filter(x -> (x.getAlbumName().equals(identifier) || x.getArtist().equals(identifier)
                        || String.valueOf(x.getYear()).equals(identifier)))
                .collect(Collectors.toList()));
    }

    public List<Duration> getAlbumDurations() {
        return getAlbums().stream().map(Album::getAlbumDuration).map(x -> {
            return new Duration() {
                {
                    add(x);
                }
            };
        }).collect(Collectors.toList());
    }

    public Duration getTotalDuration() {
        return getAlbumDurations().stream().reduce(new Duration(), (x, i) -> {
            x.add(i);
            return x;
        });
    }

    @Override
    public String toString() {
        String toString = "";
        for (Album album : albums) {
            toString += album.toString() + "\n";
        }
        return toString;
    }

}