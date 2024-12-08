import java.util.Arrays;

public class AlbumCollection {

    private Album[] albums = new Album[]{};;

    public AlbumCollection() {
        
    }

    public void appendAlbum(Album album) {
        Album[] newAlbums = Arrays.copyOf(albums, albums.length+1);
        newAlbums[albums.length] = album;
        albums = newAlbums;
    }

    public Album[] getAlbums() { return albums; }
} 