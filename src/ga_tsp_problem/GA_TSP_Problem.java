/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ga_tsp_problem;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author hp
 */
public class GA_TSP_Problem {
    static int Limit=200; // gioi han quan the
    static int MaxGeneration = 1500;
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        String filename = "D:\\\\Xuan_Thanh\\\\Toi_Uu\\\\GA_TSP_Problem\\\\Test_48.txt";
        Individual individual = FileUtils.readIndividualFromFile(filename);
        Random random = new Random();

        //khoi tao quan the
        Population p = new Population(individual);
//        for(Individual instance : p.getPopulation()){
//            System.out.println(instance.toString() );
//        }
        
        for(int i=1;i<=MaxGeneration;i++){
            GA ga = new GA(p);
            // lai ghep
            // Sinh số ngẫu nhiên từ 0.8 đến 1
            ArrayList<Individual> GenChild = new ArrayList<>();
            double CrossoverRate = 0.8 + random.nextDouble() * (1 - 0.8);
            for(int j=0; j <= (int) CrossoverRate * Limit; j++){
                Individual dad = ga.SelectByRouleteWheel();
                Individual mom = ga.SelectByRouleteWheel();
                GenChild.addAll(ga.CrossoverPMX(dad, mom));
            }
            Population populationChild = new Population(GenChild);
            GA gaChild = new GA(populationChild);
            // dot bien
            double MutationRate = 0.01 + random.nextDouble() * (0.05 - 0.01);
            for(int t = 0;t<(int)MutationRate*Limit ;t++){
                 Individual child = gaChild.SelectByRouleteWheel();
                 gaChild.Mutation(child);
            }
            // cap nhat
            p = ga.Update(p, gaChild.getPopulation());
            // loc 
//            ArrayList<Individual> newPopulation = new ArrayList();
//            for(int l=0;l<Limit;l++){
//                int tournamentSize = 4 + random.nextInt() * (Limit-5);
//                Individual instance = ga.TournamentSelection(p.getPopulation(), tournamentSize);
//                newPopulation.add(instance);
//            }
            p.sortAndFilter();
            p.shuffle();
            System.out.println("Best solution : " + p.best_solution());
        }
        
        
        
        
        
        
        
        // check ghep cap
//        System.out.println("dad: "+p.getPopulation().get(0).toString());
//        System.out.println("mom: "+p.getPopulation().get(1).toString());
//        ArrayList<Individual> child = ga.CrossoverPMX(p.getPopulation().get(0), p.getPopulation().get(1));
//        for(Individual instance : child){
//            System.out.println("child :" + instance);
//        }
    }
    
}
