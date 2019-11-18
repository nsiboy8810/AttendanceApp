package android.example.com.attendanceapp.pojo;

public class StudentClass {
    String name, reg, id;
    public StudentClass(String name,String reg, String id){
        this.name = name;
        this.reg = reg;
        this.id  = id;
    }

    public String getName() {
        return name;
    }

    public String getReg() {
        return reg;
    }

    public String getId() {
        return id;
    }
}
