import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        FileWriter myWriter;
        FileWriter myWriterSt;
        HashMap<String, String> symbolTable = new HashMap<>();
        HashSet<String> opTab = new HashSet<>();
        int startingAddress = 0;
        int counter = 5;
        int LOCCTR = 0;
        int prevLoc = 0;

        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            myWriter = new FileWriter("intermediate.txt");
            myWriterSt = new FileWriter("SymbolTable.txt");
            String line = reader.readLine();
            String[] temp = line.split("\\s+");
            String prev = temp[1];
            if (Objects.equals(temp[1], "START")) {
                LOCCTR = startingAddress = Integer.parseInt(temp[2], 16);
            }
            printer(String.valueOf(counter), Integer.toHexString(LOCCTR), temp[0], temp[1], temp[2]);
            counter += 5;
            line = reader.readLine();
            String isEnd = temp[1];
            while (isEnd != "END") {
                temp = line.split("\\s+");
                if (Objects.equals(temp[1], ".")) {
                    printer(String.valueOf(counter), Integer.toHexString(LOCCTR), temp[0], temp[1], temp[2]);
                    counter += 5;
                    continue;
                }

                LOCCTR = LOCCTR + prevLoc;
                printer(String.valueOf(counter), Integer.toHexString(LOCCTR), temp[0], temp[1], temp[2]);

                counter += 5;
                if (!Objects.equals(temp[0], "\t") && !temp[0].equals("")) {
                    if (symbolTable.containsKey(temp[0])) {
//                        throw new IOException("duplicate symbol");
                    } else {
                        symbolTable.put(temp[0], Integer.toHexString(LOCCTR));
                        myWriterSt.write(temp[0]);
                        myWriterSt.write("\t");
                        myWriterSt.write(Integer.toHexString(LOCCTR));
                        myWriterSt.write("\n");
                    }
                }
                if (Objects.equals(temp[1], "WORD")) prevLoc = 3;
                else if (Objects.equals(temp[1], "RESW")) prevLoc = 3 * Integer.parseInt(temp[2]);
                else if (Objects.equals(temp[1], "RESB")) {
                    prevLoc = Integer.parseInt(temp[2]);
                } else if (Objects.equals(temp[1], "BYTE"))
                    if (temp[2].charAt(0) == 'C')
                        prevLoc = temp[2].length() - 3;
                    else
                        prevLoc = (temp[2].length() - 3) % 2 == 0 ? (temp[2].length() - 3) / 2 : (temp[2].length() - 3) / 2 + 1;
                else prevLoc = 3;
                //else throw new IOException("invalid operation code");
                prev = temp[1];
                line = reader.readLine();
                if (line == null) break;
                else {
                    temp = line.split("\\s+");
                    isEnd = temp[1];
                }
            }
            myWriterSt.close();
            myWriter.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printer(String counter, String startingAddress, String one, String two, String three) throws IOException {
        FileWriter myWriter = new FileWriter("intermediate.txt", true);

        myWriter.write(counter);
        myWriter.write("\t");
        myWriter.write(startingAddress);
        myWriter.write("\t");
        myWriter.write(one);
        myWriter.write("\t");
        myWriter.write(two);
        myWriter.write("\t");
        myWriter.write(three);
        myWriter.write("\n");
        myWriter.close();
    }
}
//                myWriter.write(String.valueOf(counter));
//                myWriter.write("\t");
//                myWriter.write(Integer.toHexString(LOCCTR));
//                myWriter.write("\t");
//                myWriter.write(temp[0]);
//                myWriter.write("\t");
//                myWriter.write(temp[1]);
//                myWriter.write("\t");
//                myWriter.write(temp[2]);
//                myWriter.write("\n");