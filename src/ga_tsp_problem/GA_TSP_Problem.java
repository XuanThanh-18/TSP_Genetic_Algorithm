/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ga_tsp_problem;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.text.Utilities;

/**
 *
 * @author hp
 */
public class GA_TSP_Problem {

    static int Limit; // so luong ca the trong quan the
    static int MaxGeneration; // dieu kien dung ve so luong ca the
    static double MutationRate; // ti le dot bien
    static double CrossoverRate; // ti le lai ghep
    static int NoImprovementGenerationLimit; // dieu kien dung khi so luong ca the khong cai thien
    static long NoImprovementTimeLimit; // dieu kien dung khi thoi gian khong cai thien 
    static long MaxRunTime;// dieu kien dung thoi gian chay gia thuat 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String configFilename = "D:\\\\Xuan_Thanh\\\\Toi_Uu\\\\GA_TSP_Problem\\\\paramters.txt";
        FileUtils.readConfigFromFile(configFilename);
        // In ra các biến để kiểm tra
        System.out.println("Limit: " + Limit);
        System.out.println("CrossoverRate: " + CrossoverRate);
        System.out.println("MutationRate: " + MutationRate);
        System.out.println("MaxGeneration: " + MaxGeneration);
        System.out.println("NoImprovementGenerationLimit: " + NoImprovementGenerationLimit);
        System.out.println("NoImprovementTimeLimit: " + NoImprovementTimeLimit);
        System.out.println("MaxRunTime: " + MaxRunTime);

        String bestResultFilename = "D:\\\\Xuan_Thanh\\\\Toi_Uu\\\\GA_TSP_Problem\\\\result.txt";

        String filename = "D:\\\\Xuan_Thanh\\\\Toi_Uu\\\\GA_TSP_Problem\\\\Test_48.txt";
        Individual individual = FileUtils.readIndividualFromFile(filename);
        Random random = new Random();

        //khoi tao quan the
        Population p = new Population(individual);
//        for(Individual instance : p.getPopulation()){
//            System.out.println(instance.toString() );
//        }
        String resultFilename = "D:\\\\Xuan_Thanh\\\\Toi_Uu\\\\GA_TSP_Problem\\\\generation-result.txt";
        FileUtils.clearFile(resultFilename); // Xóa nội dung file trước khi bắt đầu
        int i = 0, noImprovementCount = 0;
        long nanos1 = 0;
        String bestSolutionSoFar = p.best_solution().toString();
        Instant startTime = Instant.now();

        while (i <= MaxGeneration && noImprovementCount <= NoImprovementGenerationLimit && nanos1 <= MaxRunTime) {
            GA ga = new GA(p);
            // lai ghep
            // Sinh số ngẫu nhiên từ 0.8 đến 1
            ArrayList<Individual> GenChild = new ArrayList<>();
 //           for (int j = 0; j <= CrossoverRate * Limit; j++) {
                for (int j = 0; j < p.getPopulation().size()-1; j++) {
                Individual dad = p.getPopulation().get(j);
                Individual mom = p.getPopulation().get(j + 1);
                GenChild.addAll(ga.CrossoverPMX(dad, mom));
            }
            Population populationChild = new Population(GenChild);
            GA gaChild = new GA(populationChild);
            // dot bien
            double MutationRate = 0.01 + random.nextDouble() * (0.05 - 0.01);
            for (int t = 0; t < (int) MutationRate * Limit; t++) {
                Individual child = gaChild.SelectByRouleteWheel();
                gaChild.Mutation(child);
            }
            // cap nhat
            p = ga.Update(p, gaChild.getPopulation());
            // loc bang sap xep 

            p.sortAndFilter();
            p.shuffle();

            // Kiểm tra và ghi kết quả tốt nhất
            String currentBestSolution = p.best_solution().toString();
            if (currentBestSolution.equals(bestSolutionSoFar)) {
                noImprovementCount++;
            } else {
                bestSolutionSoFar = currentBestSolution;
                noImprovementCount = 0; // Đặt lại biến đếm khi có cải thiện
            }
            String best_solution = "Gennertation : " + i + "\n" + "Best solution : " + p.best_solution().toString();
            System.out.println(best_solution);
            if (noImprovementCount > NoImprovementGenerationLimit) {
                best_solution += "\n" + "Dung do ko cai thien ";
                System.out.println("Dung do ko cai thien " + noImprovementCount);
            }
            FileUtils.writeToFile(resultFilename, best_solution);
            i++;
            Instant endTime1 = Instant.now();

            Duration duration1 = Duration.between(startTime, endTime1);
            nanos1 = duration1.getNano();

        }
        Instant endTime = Instant.now();

        Duration duration = Duration.between(startTime, endTime);
        long nanos = duration.getNano();
        String s = "Best solution : " + p.best_solution().toString() + "\n"
                + "Optimazation vaue of probem : " + Target.opimazation() + "\n"
                + "Number of generations: " + (i - 1) + "\n"
                + "Time stared: " + startTime + "\n"
                + "Time ended: " + endTime + "\n"
                + "Time run alogrithsm: " + duration + "(" + nanos + " nanoseconds)";
        FileUtils.clearFile(bestResultFilename);
        FileUtils.writeToFile(bestResultFilename, s);

    }

}
