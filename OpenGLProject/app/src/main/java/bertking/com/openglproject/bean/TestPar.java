package bertking.com.openglproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bertking on 2017/9/26.
 */

public class TestPar implements Parcelable {
    private int age;
    private String name;
    private Boolean sex;
    private Double salary;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
        dest.writeValue(this.sex);
        dest.writeValue(this.salary);
    }


    public TestPar(String name) {
        this.name = name;
    }

    public TestPar(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
        this.sex = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.salary = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<TestPar> CREATOR = new Parcelable.Creator<TestPar>() {
        @Override
        public TestPar createFromParcel(Parcel source) {
            return new TestPar(source);
        }

        @Override
        public TestPar[] newArray(int size) {
            return new TestPar[size];
        }
    };



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "TestPar{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", salary=" + salary +
                '}';
    }
}
