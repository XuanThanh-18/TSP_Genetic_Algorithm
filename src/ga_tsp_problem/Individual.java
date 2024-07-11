/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ga_tsp_problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hp
 */
public class Individual {
    
    private ArrayList<Point> solution;

    public ArrayList<Point> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Point> solution) {
        this.solution = solution;
    }

    public Individual(ArrayList<Point> solution) {
        this.solution = solution;
    }

    public Individual() {
    }
    
    // sao chep 
    public Individual(Individual original) {
        this.solution = new ArrayList<>(original.getSolution());
    }
    public double Euclid(Point x, Point y){
        double xx = Math.pow((x.getX() - y.getX()), 2) ;
        double yy = Math.pow((x.getY() - y.getY()), 2) ;
        return Math.sqrt(xx+yy);
    }
    public double Fitness(){
        double fitness =0;
        for(int i=0;i<solution.size()-1;i++ ){
            fitness += Euclid(solution.get(i), solution.get(i+1));
        }
        return fitness + Euclid(solution.get(solution.size()-1),solution.get(0) );
    }
    public boolean check(ArrayList<Point> array ){
        Set<Point> set = new HashSet<>();
        
        for (Point element : array) {
            if (!set.add(element)) {
                // Nếu phần tử không thể được thêm vào set, nghĩa là nó đã tồn tại trong set
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String s = check(solution) ? ( "---Fitness: "+Fitness()) : "---Status: False" ;
        return "Individual{" + "solution=" + solution + '}' + s;
    }

    
    
}
