package ca.qc.johnabbott.cs406;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A log entry for the logfile in Asg #1
 * @author YOU!
 */
public class Log implements Comparable<Log> {
 
    // Fields
    private Date timestamp;
    private IPAddress ipAddress;
    private String serviceName;
    private int length;

    // Constructors
    // TODO

    // Getters and setters
    // TODO

    @Override
    public int compareTo(Log rhs) {
        // TODO
        return -1;
    }
}
