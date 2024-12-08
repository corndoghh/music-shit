import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumDatabase {

    // public static Duration[] getDurations() {

    // }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner albumsFile = new Scanner(new File("./albums.txt"));

        AlbumCollection albumCollection = new AlbumCollection();

        Album currentAlbum = null;


        while (albumsFile.hasNextLine()) {
            String data = albumsFile.nextLine();
             
            Matcher matcher = Pattern.compile("(?:[0-9]{1,2}):(?:[0-5][0-9]):(?:[0-5][0-9])").matcher(data);

            if (matcher.find()) {
                assert currentAlbum != null: "Object should not be null";
                Track track = new Track(data.split(" - ")[1], matcher.group());
                currentAlbum.appendTrack(track);
            } else {
                Matcher year = Pattern.compile("\\(\\d{4}\\)").matcher(data.split(" : ")[1]);
                if (year.find()) {
                    if (currentAlbum != null) { albumCollection.appendAlbum(currentAlbum);}

                    currentAlbum = new Album(
                        data.split(" : ")[0],
                        data.split(" : ")[1].substring(0, year.start()) + data.split(" : ")[1].substring(year.end()),
                        Integer.parseInt(year.group().split("\\(")[1].split("\\)")[0])
                    );
                }

            }
        }
        System.out.println(albumCollection.getAlbums()[1].getArtist());



        albumsFile.close();

    }
}