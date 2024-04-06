package file;

import chesspiece.ChessPiece;
import chesspiece.StaticPieces;
import main.Check;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

public class IOFile {

    public static boolean checkFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.readLine() != null;
        } catch (Exception e) {
            System.out.println("Read file error: " + e.getMessage());
            return false;
        }
    }

    public static void readGame() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/file/old.txt"))) {
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/src/file/old.txt"))) {
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/src/file/setting.txt"))) {
            bufferedWriter.write(newVolume1 + " " + newVolume2);
        } catch (Exception e) {
            System.out.println("Cannot open file: " + e.getMessage());
        }
    }

    public static Vector<Integer> getVolume() {
        Vector<Integer> newVolume = new Vector<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/file/setting.txt"))) {
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
}
