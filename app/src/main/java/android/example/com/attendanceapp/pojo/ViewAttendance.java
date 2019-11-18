package android.example.com.attendanceapp.pojo;

public class ViewAttendance {
    int attendance, percent,missed;
    String courseReg;
    public ViewAttendance(String courseReg, int total, int missed, int percent){
        this.attendance =total;
        this.missed = missed;
        this.courseReg =courseReg;
        this.percent = percent;
    }

    public int getMissed() {
        return missed;
    }

    public int getPercent() {
        return percent;
    }

    public int getStudentAttendance() {
        return attendance;
    }

    public String getCourseReg() {
        return courseReg;
    }
}
