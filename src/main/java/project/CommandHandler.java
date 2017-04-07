package project;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
    The serverHandler takes care of the client commands in the run method. Each command is given a separate thread.
 */

public class CommandHandler implements Runnable {
    private Socket socket;
    private File databaseFile = new File("src/main/resources/data/Database.csv");
    private File toDoFile = new File(".");//Add relative path of your file Adwan

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

            //Create if statement for your command and add data to it. See two if statements down for examples.

            if(command.equals("Get")) {
                FileReader reader = new FileReader(databaseFile);
                BufferedReader inFromFile = new BufferedReader(reader);

                String line;

                while ((line = inFromFile.readLine()) != null) {
                    out.println(line);
                }
                inFromFile.close();
            }

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

            if(command.equals("Delete")) {
                File tempFile = new File("TempCsv.csv");

                BufferedReader reader = new BufferedReader(new FileReader(databaseFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String lineToRemove = data;
                String currentLine;

                while((currentLine = reader.readLine()) != null) {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    System.out.println(trimmedLine);
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