/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ga_tsp_problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hp
 */
public class Target {

    public static void main(String[] args) {
        String filename = "D:\\\\Xuan_Thanh\\\\Toi_Uu\\\\GA_TSP_Problem\\\\Test_48.txt";
        Individual individual = FileUtils.readIndividualFromFile(filename);
        List<Integer> result = new ArrayList<>(Arrays.asList(1, 8, 38, 31, 44, 18, 7, 28, 6, 37, 19, 27, 17, 43, 30, 36, 46, 33, 20, 47, 21, 32, 39, 48, 5, 42, 24, 10, 45, 35, 4, 26, 2, 29, 34, 41, 16, 22, 3, 23, 14, 25, 13, 11, 12, 15, 40, 9));
        ArrayList<Point> topic = individual.getSolution();
        // Khởi tạo các điểm từ result
        ArrayList<Point> points = new ArrayList<>();
        for (Integer id : result) {
            for (Point point : topic) {
                if (point.getId() == id) {
                    points.add(point);
                    break;
                }
            }
        }
        
        // Tính fitness của dãy điểm
        Individual kq= new Individual(points);
        // In ra kết quả
        System.out.println("Fitness of the sequence: " + kq.toString());
    }
}
