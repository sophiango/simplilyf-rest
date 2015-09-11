package demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by abc on 9/11/2015.
 */
@Document
public class SmartDevice {
    @Id
    private String id;
    private List<String> up;
    private List<String> down;
    private  String serial_no;
    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getId() {
        System.out.println("getId");
        return id;
    }
    public void setId(String id) {
        System.out.println("setId");
        this.id = id;
    }
    public List<String> getColdData() {
        System.out.println("Array of cold synonym");
        return down;
    }
    public void setColdData(List<String> coldname) {
        System.out.println("set cold data");
        this.down = down;
    }
    public List<String> getHotData() {
        System.out.println("Array of cold synonym");
        return up;
    }
    public void setHotData(List<String> hotname) {
        System.out.println("set warm data");
        this.up=up;
    }
}