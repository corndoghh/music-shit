import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumCollection {

    private Album[] albums = new Album[] {};

    public AlbumCollection() {
    }

    public AlbumCollection(Album[] albums) {
        this.albums = albums;
    }

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

    public void appendAlbum(Album album) {
        Album[] newAlbums = Arrays.copyOf(albums, albums.length + 1);
        newAlbums[albums.length] = album;
        albums = newAlbums;
    }

    public Album[] getAlbums() {
        return albums;
    }

    // ====== Album Sorting ======

    public void sort() {
        sort(Album.Artist, false);
    }

    public void sort(Comparator<Album> comparator) {
        sort(comparator, false);
    }

    public void sort(Comparator<Album> comparator, Boolean reversed) {
        if (comparator == Album.AlbumNameLength || comparator == Album.ArtistLength) {
            reversed = !reversed;
        }
        ArrayList<Album> sorted = new ArrayList<>(Arrays.asList(this.albums));
        Collections.sort(sorted, comparator);
        if (reversed) {
            Collections.reverse(sorted);
        }
        albums = sorted.toArray(Album[]::new);
    }

    // ====== Album Sorting ======

    public Album getAlbumByIdentifier(String identifier) {
        return getAlbumsByIdentifier(identifier).getAlbums()[0];
    }

    public AlbumCollection getAlbumsByIdentifier(String identifier) {
        Album[] filterdAlbums = Arrays.stream(albums).filter(x -> (x.getAlbumName().equals(identifier)
                || x.getArtist().equals(identifier) || String.valueOf(x.getYear()).equals(identifier)))
                .toArray(Album[]::new);

        return new AlbumCollection(filterdAlbums);
    }

    public Duration[] getAlbumDurations() {
        return Arrays.stream(getAlbums()).map(Album::getAlbumDuration).map(x -> {
            return new Duration(new int[] { 0, 0, 0 }) {
                {
                    addDuration(x);
                }
            };
        }).toArray(Duration[]::new);
    }

    public Duration getTotalDuration() {
        return Arrays.stream(getAlbumDurations()).reduce(new Duration(new int[] { 0, 0, 0 }), (x, i) -> {
            x.addDuration(i);
            return x;
        });
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
        // return new Duration(totalDuration);
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