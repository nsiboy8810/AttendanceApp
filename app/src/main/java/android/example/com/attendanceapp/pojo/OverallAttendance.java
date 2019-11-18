package android.example.com.attendanceapp.pojo;

public class OverallAttendance {
    String reg; int atttendance, total;
    public OverallAttendance(String reg,int total, int atttendance){
        this.reg = reg;
        this.total = total;
        this.atttendance  = atttendance;
    }

    public String getReg() {
        return reg;
    }

    public int getTotal() {
        return total;
    }

    public int getAtttendance() {
        return atttendance;
    }
}
