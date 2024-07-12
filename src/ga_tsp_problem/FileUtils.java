package ga_tsp_problem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    // Phương thức xóa nội dung file trước khi bắt đầu
    public static void clearFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) { // Không truyền 'true' để ghi đè
            // Không ghi gì cả, chỉ xóa nội dung file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // 'true' để ghi tiếp
            bw.write(content);
            bw.newLine(); // Thêm dòng mới để dữ liệu ghi vào mỗi dòng
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readConfigFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch (key) {
                        case "số lượng cá thể trong quần thể":
                            GA_TSP_Problem.Limit = Integer.parseInt(value);
                            break;
                        case "tỉ lệ lai ghép":
                            GA_TSP_Problem.CrossoverRate = Double.parseDouble(value);
                            break;
                        case "tỉ lệ đột biến":
                            GA_TSP_Problem.MutationRate = Double.parseDouble(value);
                            break;
                        case "điều kiện dừng về số lượng thế hệ":
                            GA_TSP_Problem.MaxGeneration = Integer.parseInt(value);
                            break;
                        case "điều kiện dừng về số lượng thế hệ không cải thiện liên tục":
                            GA_TSP_Problem.NoImprovementGenerationLimit = Integer.parseInt(value);
                            break;
                        case "điều kiện dừng về thời gian không cải thiện (s)":
                            GA_TSP_Problem.NoImprovementTimeLimit = Long.parseLong(value);
                            break;
                        case "điều kiện dừng về thời gian chạy giải thuật (s)":
                            GA_TSP_Problem.MaxRunTime = (long) (Long.parseLong(value)*1000000000);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
