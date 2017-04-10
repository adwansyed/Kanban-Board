package project;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 *this class saves the day and text for always changing textAreas
 */
public class Node2 {
    private String text;
    private String date;
    private String[] strArray;

    public Node2(String date, String text){
        this.date = date;
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
