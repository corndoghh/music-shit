import java.util.Arrays;


public class AlbumDatabase {


    public static void main(String[] args) {

        AlbumCollection albumCollection = new AlbumCollection();
        if (!albumCollection.loadFromFile("./albums.txt")) { System.out.println("Error happened bye bye :( "); return; }

        albumCollection.sort(Album.Artist);

        System.out.println("\n\nAll albums sorted by artists name: \n" + albumCollection);

        AlbumCollection kraftwerk = albumCollection.getAlbumsByIdentifier("Kraftwerk");

        System.out.println("kraftwerk's albums total duration: " + kraftwerk.getTotalDuration() + "\n");

        albumCollection.sort(Album.AlbumNameLength, true);

        System.out.println("Shortest album name: " + albumCollection.getAlbums()[0] + "\n");

        Track[] longestTracks = Arrays.stream(albumCollection.getAlbums()).map(x -> {
            x.sort(Track.TrackDuration, true);
            return x.getTracks()[0];
        }).toArray(Track[]::new);
        
        Track longestTrack = new Album(null, null, 0, longestTracks) {{ sort(Track.TrackDuration, true); }}.getTracks()[0]; 

        System.out.println("Longest track duration: " + longestTrack);

    }
}