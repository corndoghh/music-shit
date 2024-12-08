import java.util.Comparator;

public class Track extends Duration {

    private final String title;

    public Track(String title, String duration) {
        super(duration);
        this.title = title;
    }

    public String getTitle() { return title; }

    public static final Comparator<Track> TrackDuration = new Comparator<Track>() {
        @Override
        public int compare(Track t1, Track t2) {
            return t1.compareTo(t2);
        }

    };

    @Override
    public String toString() {
        return "Track Title: " + title + ", Track Duration: " + super.toString() + "\n";
    }

}
