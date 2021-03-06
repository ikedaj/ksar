package net.atomique.ksar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TreeSet;
import net.atomique.ksar.XML.OSConfig;

public abstract class AllParser {

     

    public AllParser () {

    }

    public void init (kSar hissar, String header) {
        String [] s = header.split("\\s+");
        mysar = hissar;
        ParserName = s[0];
        parse_header(header);
    }

    public AllParser(kSar hissar, String header) {
        init(hissar, header);
    }

    public int parse(String line, String[] columns) {
        System.err.println("not implemented");
        return -1;
    }

    public LocalDateTime get_startofgraph() {
        return startofgraph;
    }

    public LocalDateTime get_endofgraph() {
        return endofgraph;
    }

    public String getParserName() {
        return ParserName;
    }

     public boolean setDate(String s) {
         LocalDate currentDate;
         LocalDate startDate;
         LocalDate endDate;
        
        if (sarStartDate == null) {
            sarStartDate = s;
        }
        if (sarEndDate == null) {
            sarEndDate = s;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            currentDate = LocalDate.parse(s, formatter);

            parsedate = currentDate;

            startDate = LocalDate.parse(sarStartDate, formatter);
            endDate = LocalDate.parse(sarEndDate, formatter);

        } catch (DateTimeParseException ex) {
            System.out.println("unable to parse date " + s);
            return false;
        }

        if (currentDate.compareTo(startDate) < 0) {
            sarStartDate = s;
        }
        if (currentDate.compareTo(endDate) > 0) {
            sarEndDate = s;
        }
        return true;
    }

     public String getDate() {
        if (sarStartDate.equals(sarEndDate)) {
            return sarStartDate;
        } else {
            return sarStartDate + " to " + sarEndDate;
        }
    }

    public TreeSet<LocalDateTime> getDateSamples() {
        return DateSamples;
    }

    public String getCurrentStat() {
        return currentStat;
    }


    protected String sarStartDate = null;
    protected String sarEndDate = null;

    protected LocalDateTime startofgraph = null;
    protected LocalDateTime endofgraph =null;
    protected TreeSet<LocalDateTime> DateSamples = new TreeSet<LocalDateTime>();
    protected int firstdatacolumn =0;

    abstract public String getInfo();
    abstract public void parse_header(String s);
    abstract public void updateUITitle();
     
    protected kSar mysar = null;
    protected OSConfig myosconfig = null;
    protected String ParserName = null;

    protected LocalTime parsetime = null;
    protected LocalDate parsedate = null;

    protected String currentStat = "NONE";
    protected String dateFormat = "MM/dd/yy";
    protected String timeFormat = "HH:mm:ss";
    protected int timeColumn = 1;
}
