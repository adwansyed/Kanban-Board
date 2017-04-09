package project;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 */
public class CourseItem {

    private String courseItem;
    private String courseName;
    private float worth;
    private float yourMark;

    public CourseItem(String courseName, String courseItem, float worth, float yourMark) {
        this.courseItem = courseItem;
        this.worth = worth;
        this.yourMark = yourMark;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(String courseItem) {
        this.courseItem = courseItem;
    }

    public float getWorth() {return worth;}

    public void setWorth(float worth) {
        this.worth = worth;
    }

    public float getYourMark() {
        return yourMark;
    }

    public void setYourMark(float yourMark) {
        this.yourMark = yourMark;
    }

}
