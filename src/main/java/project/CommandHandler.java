package project;

import java.io.*;
import java.net.Socket;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 */
/*
    The CommandHandler takes care of the client commands in the run method. Each command is given a separate thread.
 */

public class CommandHandler implements Runnable {
    private Socket socket;
    private File databaseFile = new File("src/main/resources/data/Database.csv");

    public CommandHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Command and data from the client are read
            String command = in.readLine();
            String data = in.readLine();


            if(command.equals("Get")) {
                FileReader reader = new FileReader(databaseFile);
                BufferedReader inFromFile = new BufferedReader(reader);

                String line;

                while ((line = inFromFile.readLine()) != null) {
                    out.println(line);
                }
                inFromFile.close();
            }

            //New item that the user wrote is added to the databse on the server.
            if(command.equals("Add")) {
                try {
                    FileWriter fw = new FileWriter(databaseFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter outToFile = new PrintWriter(bw);
                    outToFile.println(data);

                    outToFile.close();
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Temp file is created. Every line except the file that we want to delete is added to tempfile.
            //TempFile is then converted to Database.csv
            if(command.equals("Delete")) {
                File tempFile = new File("TempCsv.csv");

                BufferedReader reader = new BufferedReader(new FileReader(databaseFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String lineToRemove = data;
                String currentLine;

                while((currentLine = reader.readLine()) != null) {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if(trimmedLine.equals(lineToRemove)) continue;
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                tempFile.renameTo(databaseFile);
            }

            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}