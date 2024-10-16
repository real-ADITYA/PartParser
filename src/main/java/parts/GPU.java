package parts;

import javax.persistence.*;

/** This class represents a GPU (Graphics Processing Unit) object. This data is extracted from the MySQL server. **/
@Entity
@Table(name = "gpu")
public class GPU {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bus;
    private String memory;

    @Column(name = "gpu_chip")
    private String gpuChip;

    @Column(name = "gpu_clock")
    private String gpuClock;

    @Column(name = "memory_clock")
    private String memoryClock;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "shaders_tmus_rops")
    private String shadersTmusRops;

    /** Constructors **/
    public GPU() {}

    public GPU(String productName, String gpuChip, String bus, String memory, String gpuClock,
               String memoryClock, String shadersTmusRops) {
        this.productName = productName;
        this.gpuChip = gpuChip;
        this.bus = bus;
        this.memory = memory;
        this.gpuClock = gpuClock;
        this.memoryClock = memoryClock;
        this.shadersTmusRops = shadersTmusRops;
    }

    /** Getter Methods **/
    public String getProductName() { return productName; }
    public String getGpuChip() { return gpuChip; }
    public String getBus() { return bus; }
    public String getMemory() { return memory; }

    /* These fields need to be converted to Integers, since there are complications. */
    public Integer getMemorySize() {
        String raw_string = memory.replaceAll("[\" GB]",  "");
        return Integer.valueOf(raw_string);
    }
    public Integer getMemoryBitrate() {
        String raw_string = memory.replaceAll(" bit\"", "");
        return Integer.valueOf(raw_string.replaceAll(" ", ""));
    }
    public Integer getGPUClock() {
        String raw_string = gpuClock;
        return Integer.valueOf(raw_string.split(" MHz")[0]);
    }
    public Integer getMemoryClock() {
        String raw_string = memoryClock;
        return Integer.valueOf(raw_string.split(" MHz")[0]);
    }
    public Integer getShaders() {
        return Integer.valueOf(shadersTmusRops.split(" / ")[0]);
    }
    public Integer getTMUs() {
        return Integer.valueOf(shadersTmusRops.split(" / ")[1]);
    }
    public Integer getROPs() {
        return Integer.valueOf(shadersTmusRops.split(" / ")[2]);
    }


    @Override
    public String toString() {
    	return "Name: " + this.getProductName() + 
                "\nGPU Chip: " + this.getGpuChip() + 
                "\nBus: " + this.getBus() + 
                "\nMemory: " + this.getMemory() + 
                "\nGPU Clock: " + this.getGpuChip() + 
                "\nMemory Clock: " + this.getMemoryClock() + 
                "\nShaders: " + this.getShaders() + 
                "\nTMUs: " + this.getTMUs(); 
                //"\nROPs: " + this.getROPs();
    }
}
