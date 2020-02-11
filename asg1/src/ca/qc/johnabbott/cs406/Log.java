package ca.qc.johnabbott.cs406;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * A log entry for the logfile in Asg #1
 * @author Matei Anghel
 */
public class Log implements Comparable<Log> {
    // Constants
    private final String[] PROTOCOLS = {"http", "https", "ssh", "ftp"};

    // Fields
    private IPAddress ipAddress;
    private String serviceName;
    private Date timestamp;
    private int length;

    // Constructors
    // TODO
    public Log(String line) throws ParseException {
        String[] elements = line.split("\\s+");
        this.setIpAddress(elements[0]);
        this.setServiceName(elements[1]);
        this.setTimestamp(elements[2], elements[3]);
        this.setLength(elements[4]);
    }

    // Getters and setters
    private void setIpAddress(String ipString){
        this.ipAddress = new IPAddress(ipString);
    }

    private void setServiceName(String serviceName) throws RuntimeException{
        if(!Arrays.asList(PROTOCOLS).contains(serviceName))
            throw new RuntimeException("Invalid Service Protocol: " + serviceName);
        this.serviceName = serviceName;
    }

    private void setTimestamp(String date, String time) throws ParseException {
        String inputDate = date + " " + time;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(inputDate);
    }

    private void setLength(String length){
        this.length = Integer.parseUnsignedInt(length);
    }


    /**
     * Concise implementation courtesy of an very high-IQ student.
     * @author NLV
     */
    @Override
    public int compareTo(Log rhs) {
        int compareResult;

        if ((compareResult = this.ipAddress.compareTo(rhs.ipAddress)) != 0) return compareResult;
        if ((compareResult = this.serviceName.compareTo(rhs.serviceName)) != 0) return  compareResult;

        return this.timestamp.compareTo(rhs.timestamp);
    }

    @Override
    public String toString(){
        return this.ipAddress.toString() + "\t" + serviceName + "\t" + timestamp.toString();
    }


}