public class Duration implements Comparable<Duration> {


    private final int seconds;
    private final int minutes;
    private final int hours;

    public Duration(String duration) {
        String[] timeParts = duration.split(":");

        seconds = Integer.parseInt(timeParts[2]);
        minutes = Integer.parseInt(timeParts[1]);
        hours = Integer.parseInt(timeParts[0]);
    }

    public int[] getDuration() { return new int[]{seconds, minutes, hours}; }



    @Override
    public int compareTo(Duration o) {
        int thisTotalSeconds = this.hours * 3600 + this.minutes * 60 + this.seconds;
        int otherTotalSeconds = o.hours * 3600 + o.minutes * 60 + o.seconds;

        return Integer.compare(thisTotalSeconds, otherTotalSeconds);
    }

}