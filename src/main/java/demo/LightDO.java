package demo;

    import java.util.Date;

    public class LightDO {

        // Light Status
        private int id;
        private String name;
        private String modelid;
        private boolean on;
        private int bri ;
        private int hue;
        private int sat;
        private boolean reachable;
        private String type;

        // Light Config
        private Date last_use_date;
        private Date created_date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDevice_id() {
            return id;
        }

        public void setDevice_id(int id) {
            this.id = id;
        }

        public String getModel_id() {
            return modelid;
        }

        public void setModel_id(String modelid) {
            this.modelid = modelid;
        }

        public boolean getOnStatus() {
            return on;
        }

        public void setOnStatus(Boolean on) {
            this.on = on;
        }

        public int getBrightness() {
            return bri;
        }

        public void setBrightness(int bri) {
            this.bri = bri;
        }

        public int getSaturation() {
            return sat;
        }

        public void setSaturation(int sat) {
            this.sat = sat;
        }

        public int getHueColor() {
            return hue;
        }

        public void setHueColor(int hue) {
            this.hue = hue;
        }

        public Boolean getReachableStatus() {
            return reachable;
        }

        public void setReachableStatus(Boolean reachable) {
            this.reachable = reachable;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Date getLast_use_date(){
            return last_use_date;
        }

        public void setLast_use_date(Date last_use_date){
            this.last_use_date = last_use_date;
        }

        public Date getCreated_date(){
            return created_date;
        }

        public void setCreated_date(Date created_date){
            this.created_date = created_date;
        }
    }
