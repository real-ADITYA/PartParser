package parts;

import javax.persistence.*;

/** This class represents a CPU (Central Processing Unit) object. This data is extracted from the MySQL server. **/
@Entity
@Table(name = "cpu")
public class CPU {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name, codename, cores, clock, socket, process, l3_Cache, tdp;

    /** Constructors **/
    public CPU() {}

    public CPU(String name, String codename, String cores, String clock, String socket, String process,
               String l3_Cache, String tdp){
        this.name = name;
        this.codename = codename;
        this.cores = cores;
        this.clock = clock;
        this.socket = socket;
        this.process = process;
        this.l3_Cache = l3_Cache;
        this.tdp = tdp;
    }

    /** The following are getter methods that would be useful when accessing a CPU object. **/
    /* These fields can just be returned, as they are strings. */
    public String getName() { return name; }
    public String getCodename() { return codename; }
    public String getSocket() { return socket; }

    /* These fields need to be converted to Integers, since there are complications. */
    public Integer getCores() {
        String raw_string = cores;
        if(raw_string.contains("/")) {
            return Integer.valueOf(raw_string.split(" / ")[0]);
        } else {
            return Integer.valueOf(raw_string);
        }
    }
    public Integer getThreads() {
        String raw_string = cores;
        if(raw_string.contains("/")) {
            return Integer.valueOf(raw_string.split(" / ")[1]);
        } else {
            return Integer.valueOf(raw_string);
        }
    }
    public Integer getProcess() {
        return Integer.valueOf(process.split(" nm")[0]);
    }
    public Integer get_L3() {
        String raw_string = l3_Cache;
        if(raw_string.contains("N/A")) {
            return 0;
        } else {
            return Integer.valueOf(raw_string.split(" MB")[0]);
        }
    }
    public Integer getTDP() {
        return Integer.valueOf(tdp.split(" W")[0]);
    }

    /* These fields need to be converted to Doubles, since there are complications. */
    public Double getBaseClock() {
        String raw_string = clock;
        if(raw_string.contains(" to ")) {
            return Double.valueOf(raw_string.split(" ")[0]);
        } else {
            return Double.valueOf(raw_string.split(" GHz")[0]);
        }
    }
    public Double getBoostClock() {
        String raw_string = clock;
        if(raw_string.contains(" to ")) {
            return Double.valueOf(raw_string.split(" ")[2]);
        } else {
            return Double.valueOf(raw_string.split(" GHz")[0]);
        }
    }

    /* Gaming score is calculated taking clock speed, core count, and cache as the main factors. */
    public Double getGScore(){
        return (1.0 * getBaseClock()) + (1.5 * getBoostClock()) + (0.75 * getCores())
                + (2.5 * get_L3()) - (0.1 * getTDP());
    }
    /* Productivity score is calculated taking cores, threads, and cache as the main factors. */
    public Double getPScore(){
        return (2.0 * getCores()) + (1.5 * getThreads()) + (1.2 * get_L3()) + (1.0 * getBaseClock())
                - (0.1 * getTDP());
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + 
                "\nCodename: " + this.getCodename() + 
                "\nCores: " + this.getCores() + 
                "\nBase Clock: " + this.getBaseClock() + 
                "\nBoost Clock: " + this.getBoostClock() + 
                "\nSocket: " + this.getSocket() + 
                "\nProcess: " + this.getProcess() + 
                "\nL3 Cache: " + this.get_L3() + 
                "\nTDP: " + this.getTDP() + 
                "\n\n-----\n\nGaming Score: " + String.format("%.5g%n", this.getGScore()) +
                "\nProductivity Score: " + String.format("%.5g%n", this.getPScore());
    }
}
