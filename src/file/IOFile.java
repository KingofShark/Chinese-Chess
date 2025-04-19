package file;

import model.ChessPiece;
import controller.StaticPieces;
import model.Check;

import java.io.*;
import java.util.Vector;

public class IOFile {

    public static boolean isEmpty(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.readLine() == null;
        } catch (Exception e) {
            System.out.println("Read file error: " + e.getMessage());
            return true;
        }
    }

    public static void readGame() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resource/file/old.txt"))) {
            Check check = StaticPieces.getCheck();
            Vector<ChessPiece> pieces = StaticPieces.getPieces();
            for (int i = 0; i < 10; i++) {
                String line = bufferedReader.readLine();
                if (line == null)
                    return;
                String[] temp = line.split(" ");
                for (int j = 0; j < 9; j++) {
                    int pieceIndex = Integer.parseInt(temp[j]);
                    if (pieceIndex != -1) {
                        check.setPiece(j, i, pieceIndex);
                        pieces.elementAt(pieceIndex).setLocate(j, i);
                        pieces.elementAt(pieceIndex).setVisible(true);
                        StaticPieces.getChessBoardPanel().add(pieces.elementAt(pieceIndex));
                    }
                    if(pieceIndex == 18)
                        System.out.println(pieces.elementAt(pieceIndex));
                }
            }
            for(int i = 0; i < 10; i++){
                for (int j = 0; j < 9; j ++){
                    System.out.print(check.getPiece(j,i) + " ");
                }
                System.out.println();
            }

            StaticPieces.setTurn(Integer.parseInt(bufferedReader.readLine()));
            String[] temp = bufferedReader.readLine().split(" ");
            StaticPieces.getClock_1().setTime(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            StaticPieces.getChessBoardPanel().setTime(0, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            temp = bufferedReader.readLine().split(" ");
            StaticPieces.getClock_2().setTime(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            StaticPieces.getChessBoardPanel().setTime(1, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
        } catch (Exception e) {
            System.out.println("Read file error: " + e.getMessage());
        }
    }

    public static void saveGame() {
        IOFile.deleteLastMove();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resource/file/old.txt"))) {
            if(StaticPieces.getTurn() == -1){
                bufferedWriter.close();
                return;
            }
            Check check = StaticPieces.getCheck();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++)
                    bufferedWriter.write(check.getPiece(j, i) + " ");
                bufferedWriter.write("\n");
            }
            for(int i = 0; i < 10; i++){
                for (int j = 0; j < 9; j ++){
                    System.out.print(check.getPiece(j,i) + " ");
                }
                System.out.println();
            }
            bufferedWriter.write(StaticPieces.getTurn() + "\n");
            bufferedWriter.write(StaticPieces.getClock_1().getMinute() + " " + StaticPieces.getClock_1().getSecond() + "\n");
            bufferedWriter.write(StaticPieces.getClock_2().getMinute() + " " + StaticPieces.getClock_2().getSecond());
            System.out.println("Save Game successfully");
        } catch (Exception e) {
            System.out.println("Cannot open file: " + e.getMessage());
        }
    }

    public static void saveVolume(int newVolume1, int newVolume2) {
        int minute = IOFile.getTime().firstElement();
        int second = IOFile.getTime().lastElement();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resource/file/setting.txt"))) {
            bufferedWriter.write(newVolume1 + " " + newVolume2 + "\n" + minute + " " + second);
        } catch (Exception e) {
            System.out.println("Cannot open file: " + e.getMessage());
        }
    }

    public static Vector<Integer> getVolume() {
        Vector<Integer> newVolume = new Vector<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resource/file/setting.txt"))) {
            String line = bufferedReader.readLine();
            if (line == null) {
                newVolume.add(100);
                newVolume.add(100);
            } else {
                newVolume.add(Integer.parseInt(line.split(" ")[0]));
                newVolume.add(Integer.parseInt(line.split(" ")[1]));
            }
        } catch (Exception e) {
            System.out.println("Read file error: " + e.getMessage());
        }
        return newVolume;
    }
    public static Vector<Integer> getTime() {
        Vector<Integer> newTime = new Vector<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resource/file/setting.txt"))) {
            String line = bufferedReader.readLine();
            if (line == null) {
                newTime.add(1);
                newTime.add(0);
            } else {
                line = bufferedReader.readLine();
                if(line != null) {
                    newTime.add(Integer.parseInt(line.split(" ")[0]));
                    newTime.add(Integer.parseInt(line.split(" ")[1]));
                }else{
                    newTime.add(1);
                    newTime.add(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Read file error: " + e.getMessage());
        }
        return newTime;
    }
    public static void saveTime(int minute, int second){
        int vol1 = IOFile.getVolume().firstElement();
        int vol2 = IOFile.getVolume().lastElement();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resource/file/setting.txt"))) {
            bufferedWriter.write(vol1 + " " + vol2 + "\n" + minute + " " + second);
        } catch (Exception e) {
            System.out.println("Cannot open file: " + e.getMessage());
        }
    }
    public static Vector<Integer> loadLastMove(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resource/file/lastmove.txt"));
            String line = bufferedReader.readLine();
            String[] temp = line.split(" ");
            Vector<Integer> last = new Vector<>();
            last.add(Integer.parseInt(temp[0]));
            last.add(Integer.parseInt(temp[1]));
            last.add(Integer.parseInt(temp[2]));
            last.add(Integer.parseInt(temp[3]));
            last.add(Integer.parseInt(temp[4]));
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/resource/file/lastmove.txt", false);
            fileWriter.write("");
            fileWriter.close();
            return last;
        } catch (IOException e) {
            return new Vector<>();
        }
    }

    public static Vector<Integer> getLastMove(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resource/file/lastmove.txt"));
            String line = bufferedReader.readLine();
            String[] temp = line.split(" ");
            Vector<Integer> last = new Vector<>();
            last.add(Integer.parseInt(temp[0]));
            last.add(Integer.parseInt(temp[1]));
            last.add(Integer.parseInt(temp[2]));
            last.add(Integer.parseInt(temp[3]));
            last.add(Integer.parseInt(temp[4]));
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/resource/file/lastmove.txt", false);
            fileWriter.write("");
            fileWriter.close();
            return last;
        } catch (IOException e) {
            return new Vector<>();
        }
    }

    public static void saveLastMove(int move, int x, int y, int kill, int turn){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resource/file/lastmove.txt"));
            bufferedWriter.write(move + " " + x + " " + y + " " + kill + " " + turn);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteLastMove(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resource/file/lastmove.txt"));
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}