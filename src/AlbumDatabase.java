import java.util.stream.Collectors;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class AlbumDatabase {

    public static boolean loadFromFile(String filePath, AlbumCollection albumCollection) {
        Scanner albumsFile;
        try {
            albumsFile = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        Album album = null;

        while (albumsFile.hasNextLine()) {
            String data = albumsFile.nextLine();

            Matcher matcher = Pattern.compile("(?:[0-9]{1,2}):(?:[0-5][0-9]):(?:[0-5][0-9])").matcher(data);

            if (matcher.find()) {
                assert album != null : "Object should not be null";
                Track track = new Track(data.split(" - ")[1], matcher.group());
                album.appendTrack(track);
            } else {
                Matcher year = Pattern.compile("\\(\\d{4}\\)").matcher(data.split(" : ")[1]);
                if (year.find()) {
                    if (album != null) {
                        albumCollection.appendAlbum(album);
                    }
                    album = new Album(
                            data.split(" : ")[0],
                            data.split(" : ")[1].substring(0, year.start() - 1),
                            Integer.parseInt(year.group().replaceAll("[()]", "")));
                }

            }
        }
        albumsFile.close();
        return true;
    }


    public static void main(String[] args) {

        AlbumCollection albumCollection = new AlbumCollection();
        if (!loadFromFile("./albums.txt", albumCollection)) {
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