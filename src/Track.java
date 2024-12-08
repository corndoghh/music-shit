public class Track extends Duration {

    private final String title;

    public Track(String title, String duration) {
        super(duration);
        this.title = title;
    }

    public String getTitle() { return title; }

}
