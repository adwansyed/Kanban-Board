package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

//This is the client class where all commands are sent to the server.

public class Client {

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //returns an observable list with all the courses the user has wrote on the application during  previous session
    public ObservableList<Course> getData(String command){
        ObservableList<String> courseNames = FXCollections.observableArrayList();
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            Socket client = new Socket(this.host, this.port);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println(command);
            out.println("");

            String line;

            while ((line = in.readLine()) != null) {
                String[] dataFields = line.split(",");
                if(courseNames.contains(dataFields[0])) {
                    for(Course c: courses) {
                        if (c.getName().equals(dataFields[0])) {
                            c.getMarks().add(new CourseItem(dataFields[0],dataFields[1],Float.parseFloat(dataFields[2]),Float.parseFloat(dataFields[3])));
                        }
                    }
                }
                else {
                    courseNames.add(dataFields[0]);
                    Course newCourse = new Course(dataFields[0]);
                    newCourse.getMarks().add(new CourseItem(dataFields[0],dataFields[1],Float.parseFloat(dataFields[2]),Float.parseFloat(dataFields[3])));
                    courses.add(newCourse);
                }
            }

            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    //Will tell server to add or delete data.
    public void submitQuery(String command, String data) {
        try {
            Socket client = new Socket(this.host, this.port);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println(command);
            out.println(data);

            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}