import java.util.Comparator;

public class Track extends Duration {

    private final String title;

    public Track(String title, String duration) {
        super(duration);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static final Comparator<Track> TRACK_DURATION = Comparator.naturalOrder();

    @Override
    public String toString() {
        return "Track Title: " + title + ", Track Duration: " + super.toString() + "\n";
    }

}
