package ga_tsp_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

    public static Individual readIndividualFromFile(String filename) {
        Individual individual = new Individual();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            ArrayList<Point> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] pointString = line.split(" ");
                if (pointString.length == 3) {
                    int id = Integer.parseInt(pointString[0]);
                    double x = Double.parseDouble(pointString[1]);
                    double y = Double.parseDouble(pointString[2]);
                    Point point = new Point(id, x, y);
                    list.add(point);
                } else {
                    System.out.println("Có lỗi khi đọc file");
                }
            }
            individual.setSolution(list);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return individual;
    }
}
