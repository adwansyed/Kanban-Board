package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 *  this class acts as a Global Observable list for TableViewTextArea
 */
public class Observe {

    public ObservableList<Node2> check;
    public String text;
    public String[] strArray;
    public String date;


    public Observe(){

        check = FXCollections.observableArrayList();

    }

    public void add(String text) {
        this.text = text;
        System.out.println(text + ".");
        strArray = text.split(" ");
       System.out.println(strArray.length);
        date = (strArray[0]);
        if (strArray.length > 2){
            text = strArray[2];
        }
        check.add(new Node2(date, text));

    }
    public ObservableList<Node2> getObserve(){
        return check;
    }
}
