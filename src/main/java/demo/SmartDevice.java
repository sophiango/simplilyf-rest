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
    public List<String> getUp() {
        return up;
    }

    public void setUp(List<String> up) {
        this.up = up;
    }
    public List<String> getDown() {
        return down;
    }

    public void setDown(List<String> down) {
        this.down = down;
    }
    public String getId() {
        System.out.println("getId");
        return id;
    }
    public void setId(String id) {
        System.out.println("setId");
        this.id = id;
    }
}