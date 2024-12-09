public class Duration implements Comparable<Duration> {


    private int seconds;
    private int minutes;
    private int hours;

    public String toString() {
        String seconds = String.valueOf(this.seconds);
        String minutes = String.valueOf(this.minutes);
        String hours = String.valueOf(this.hours);
        return (hours.length() == 1 ? "0"+hours : hours)+":"
        +(minutes.length() == 1 ? "0"+minutes : minutes)+":"
        +(seconds.length() == 1 ? "0"+seconds : seconds);
    }
    
    public Duration(String duration) {
        String[] timeParts = duration.split(":");

        seconds = Integer.parseInt(timeParts[2]);
        minutes = Integer.parseInt(timeParts[1]);
        hours = Integer.parseInt(timeParts[0]);
    }

    public Duration() { this(new int[]{0, 0, 0}); }

    public Duration(int[] duration) {
        seconds = duration[0];
        minutes = duration[1];
        hours = duration[2];
    }

    public int[] toInt() { return new int[]{seconds, minutes, hours}; }

    public int toSeconds() { return seconds + minutes * 60 + hours * 3600; }

    public void add(Duration d2) {
        this.seconds += d2.seconds;
        this.minutes += d2.minutes;
        this.hours += d2.hours;

        this.minutes += this.seconds / 60;
        this.seconds %= 60;
        this.hours += this.minutes / 60;
        this.minutes %= 60;
    } 

    // Sorting

    @Override
    public int compareTo(Duration d2) { return Integer.compare(this.toSeconds(), d2.toSeconds()); }

}