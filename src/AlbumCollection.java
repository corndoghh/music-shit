import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlbumCollection {

    private List<Album> albums;

    
    public AlbumCollection(List<Album> albums) { this.albums = albums; }

    public AlbumCollection() { this(new ArrayList<>()); }
    
    public boolean loadFromFile(String filePath) {
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
                        this.appendAlbum(album);
                    }

                    album = new Album(
                            data.split(" : ")[0],
                            data.split(" : ")[1].substring(0, year.start()),
                            Integer.parseInt(year.group().split("\\(")[1].split("\\)")[0]));
                }

            }
        }
        albumsFile.close();
        return true;

    }

    public void appendAlbum(Album album) { albums.add(album); }

    public List<Album> getAlbums() { return albums; }

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
        if (reversed) { Collections.reverse(albums); }
    }

    // ====== Album Sorting ======

    public Album getAlbumByIdentifier(String identifier) {
        return getAlbumsByIdentifier(identifier).getAlbums().get(0);
    }

    public AlbumCollection getAlbumsByIdentifier(String identifier) {
        Stream<Album> a = albums
            .stream()
            .filter(x -> (x.getAlbumName().equals(identifier) || x.getArtist().equals(identifier) || String.valueOf(x.getYear()).equals(identifier)));
        
        ArrayList<Album> b = (ArrayList<Album>) a.collect(Collectors.toList());
        return new AlbumCollection(b);
        // return new AlbumCollection(albums.stream().filter(x -> (x.getAlbumName().equals(identifier)
        //         || x.getArtist().equals(identifier) || String.valueOf(x.getYear()).equals(identifier)))
        //         .toArray(Album[]::new));
    }

    public List<Duration> getAlbumDurations() {
        return getAlbums().stream().map(Album::getAlbumDuration).map(x -> {
            return new Duration() {
                { add(x); }
            };
        }).collect(Collectors.toList());
        // return Arrays.stream(getAlbums()).map(Album::getAlbumDuration).map(x -> {
        //     return new Duration(new int[] { 0, 0, 0 }) {
        //         { add(x); }
        //     };
        // }).toArray(Duration[]::new);
    }

    public Duration getTotalDuration() { return getAlbumDurations().stream().reduce(new Duration(), (x, i) -> { x.add(i); return x; }); }
        // System.out.println(totalDuration);

        // return new Duration(new int[]{0,0,0});
        // int[] totalDuration = IntStream.range(0, 3).boxed()
        // .collect(Collectors.toMap(
        // i -> i,
        // i -> IntStream.range(0, getAlbums().length)
        // .map(x -> getAlbums()[x].getAlbumDuration().toInt()[i])
        // .sum()))
        // .values().stream()
        // .mapToInt(x -> ((Integer) x).intValue())
        // .toArray();

        // totalDuration[1] += totalDuration[0] / 60;
        // totalDuration[0] %= 60;

        // totalDuration[2] += totalDuration[1] / 60;
        // totalDuration[1] %= 60;
        // return new Duration(totalDuration)

    @Override
    public String toString() {
        String toString = "";
        for (Album album : albums) {
            toString += album.toString() + "\n";
        }
        return toString;
    }

}