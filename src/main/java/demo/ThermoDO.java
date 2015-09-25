package demo;

import com.sun.istack.internal.NotNull;

import java.security.Timestamp;
import java.util.UUID;

public class ThermoDO {
	private String name;
    private UUID where_id;
    @NotNull
	private String device_id;

    // DESIRED TEMPERATURE
	private int target_temperature_f;
	private float target_temperature_c;
	private int target_temperature_high_f;
	private float target_temperature_high_c;

	private int target_temperature_low_f;
	private float target_temperature_low_c;

    // TEMPERATURE SET FOR AWAY MODE
	private int away_temperature_high_f;
	private float away_temperature_high_c;
	private int away_temperature_low_f;
	private float away_temperature_low_c;

    // TEMPERATURE MEASURE AT DEVICE
	private int ambient_temperature_f;
	private float ambient_temperature_c;

	private int humidity;

	private boolean can_cool;
	private boolean can_heat;
	private boolean has_leaf;
	private String hvac_mode; // heat/cool
	private boolean is_using_emergency_heat;
	private boolean has_fan;

    private Timestamp timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getWhere_id() {
        return where_id;
    }

    public void setWhere_id(UUID where_id) {
        this.where_id = where_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getTarget_temperature_f() {
        return target_temperature_f;
    }

    public void setTarget_temperature_f(int target_temperature_f) {
        this.target_temperature_f = target_temperature_f;
    }

    public float getTarget_temperature_c() {
        return target_temperature_c;
    }

    public void setTarget_temperature_c(float target_temperature_c) {
        this.target_temperature_c = target_temperature_c;
    }

    public int getTarget_temperature_high_f() {
        return target_temperature_high_f;
    }

    public void setTarget_temperature_high_f(int target_temperature_high_f) {
        this.target_temperature_high_f = target_temperature_high_f;
    }

    public float getTarget_temperature_high_c() {
        return target_temperature_high_c;
    }

    public void setTarget_temperature_high_c(float target_temperature_high_c) {
        this.target_temperature_high_c = target_temperature_high_c;
    }

    public int getTarget_temperature_low_f() {
        return target_temperature_low_f;
    }

    public void setTarget_temperature_low_f(int target_temperature_low_f) {
        this.target_temperature_low_f = target_temperature_low_f;
    }

    public float getTarget_temperature_low_c() {
        return target_temperature_low_c;
    }

    public void setTarget_temperature_low_c(float target_temperature_low_c) {
        this.target_temperature_low_c = target_temperature_low_c;
    }

    public int getAway_temperature_high_f() {
        return away_temperature_high_f;
    }

    public void setAway_temperature_high_f(int away_temperature_high_f) {
        this.away_temperature_high_f = away_temperature_high_f;
    }

    public float getAway_temperature_high_c() {
        return away_temperature_high_c;
    }

    public void setAway_temperature_high_c(float away_temperature_high_c) {
        this.away_temperature_high_c = away_temperature_high_c;
    }

    public int getAway_temperature_low_f() {
        return away_temperature_low_f;
    }

    public void setAway_temperature_low_f(int away_temperature_low_f) {
        this.away_temperature_low_f = away_temperature_low_f;
    }

    public float getAway_temperature_low_c() {
        return away_temperature_low_c;
    }

    public void setAway_temperature_low_c(float away_temperature_low_c) {
        this.away_temperature_low_c = away_temperature_low_c;
    }

    public int getAmbient_temperature_f() {
        return ambient_temperature_f;
    }

    public void setAmbient_temperature_f(int ambient_temperature_f) {
        this.ambient_temperature_f = ambient_temperature_f;
    }

    public float getAmbient_temperature_c() {
        return ambient_temperature_c;
    }

    public void setAmbient_temperature_c(float ambient_temperature_c) {
        this.ambient_temperature_c = ambient_temperature_c;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public boolean isCan_cool() {
        return can_cool;
    }

    public void setCan_cool(boolean can_cool) {
        this.can_cool = can_cool;
    }

    public boolean isCan_heat() {
        return can_heat;
    }

    public void setCan_heat(boolean can_heat) {
        this.can_heat = can_heat;
    }

    public boolean isHas_leaf() {
        return has_leaf;
    }

    public void setHas_leaf(boolean has_leaf) {
        this.has_leaf = has_leaf;
    }

    public String getHvac_mode() {
        return hvac_mode;
    }

    public void setHvac_mode(String hvac_mode) {
        this.hvac_mode = hvac_mode;
    }

    public boolean is_using_emergency_heat() {
        return is_using_emergency_heat;
    }

    public void setIs_using_emergency_heat(boolean is_using_emergency_heat) {
        this.is_using_emergency_heat = is_using_emergency_heat;
    }

    public boolean isHas_fan() {
        return has_fan;
    }

    public void setHas_fan(boolean has_fan) {
        this.has_fan = has_fan;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
