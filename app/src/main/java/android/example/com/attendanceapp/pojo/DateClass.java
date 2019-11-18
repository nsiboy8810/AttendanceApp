package android.example.com.attendanceapp.pojo;

public class DateClass {
    String course, date;
    public DateClass(String course, String date) {
        this.course = course;
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public String getDate() {
        return date;
    }
}
