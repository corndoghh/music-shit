import java.util.stream.Collectors;

public class AlbumDatabase {

    public static void main(String[] args) {

        AlbumCollection albumCollection = new AlbumCollection();
        if (!albumCollection.loadFromFile("./albums.txt")) {
            System.out.println("Error happened bye bye :( ");
            return;
        }

        albumCollection.sort(Album.ARTIST);

        System.out.println("\n\nAll albums sorted by artists name: \n" + albumCollection);

        AlbumCollection kraftwerk = albumCollection.getAlbumsByIdentifier("Kraftwerk");

        System.out.println("kraftwerk's albums total duration: " + kraftwerk.getTotalDuration() + "\n");

        albumCollection.sort(Album.ALBUM_NAME_LENGTH, true);

        System.out.println("Shortest album name: " + albumCollection.getAlbums().get(0) + "\n");

        Track longestTrack = new Album(null, null, 0, albumCollection.getAlbums().stream().map(x -> {
            x.sort(Track.TRACK_DURATION, true);
            return x.getTracks().get(0);
        }).collect(Collectors.toList())) {
            {
                sort(Track.TRACK_DURATION, true);
            }
        }.getTracks().get(0);

        System.out.println("Longest track duration: " + longestTrack);

    }
}