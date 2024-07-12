/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ga_tsp_problem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author hp
 */
public class Population {
    
    private int limit = GA_TSP_Problem.Limit; // so luong ca the trong quan the;
    private ArrayList<Individual> population;
    public Population(){
    }

    public Population(ArrayList<Individual> population) {
        this.population = population;
    }
    
    public Population( Individual solution) {
        this.population = Gen_Population(solution);
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }
    
    public ArrayList<Individual> Gen_Population (Individual solution){
        ArrayList<Individual> p = new ArrayList();
        for(int i=0;i<limit;i++){
            Individual clonedSolution = new Individual(solution); // Tạo bản sao của solution
            Collections.shuffle(clonedSolution.getSolution());
            p.add(clonedSolution);
        }
        return p;
    }
    public Individual best_solution(){
        return Collections.min(population, Comparator.comparingDouble(Individual::Fitness));
    }
      public void sortAndFilter() {
        // Sắp xếp population theo độ thích nghi giảm dần
        population.sort((a, b) -> Double.compare(a.Fitness(), b.Fitness()));
        // Giữ lại chỉ limit phần tử
        if (population.size() > limit) {
            population.subList(limit, population.size()).clear();
        }
    }
    
    // Trộn lại population sau khi sắp xếp và lọc
    public void shuffle() {
        Collections.shuffle(population);
    }
}
