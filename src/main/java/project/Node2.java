package project;

/**
 * Created by andrew on 07/04/17.
 */
public class Node2 {
    private String text;
    private String date;
    private String[] strArray;

    public Node2(String date, String text){
        this.date = date;
        this.text = text;
/*        int counter2 = 0;
        strArray = text.split(" ");
        for (int counter = 0; counter < strArray.length; counter++) {
            //System.out.println(strArray[counter]+",");
            counter2++;
        }
        date = Integer.parseInt(strArray[0]);
        if(counter2> 1) {
          text = strArray[1];
        }
        //System.out.println(date);*/
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
