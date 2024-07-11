/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ga_tsp_problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author hp
 */
public class GA {

    static float CrossoverRate = 0.7f; // ti le trao doi cheo : 0.8f  1
    static float MutationRate = 0.01f; //ti le dot bien:  0.05f

    private Population population;

    public Population getPopulation() {
        return population;
    }
    
    public GA(Population population) {
        this.population = population;
    }

    public Individual SelectByRouleteWheel() {
        if (population == null) {
            return null; // Trả về null nếu quần thể chưa được khởi tạo
        }

        ArrayList<Individual> listIndividual = population.getPopulation(); // Lấy danh sách các cá thể từ quần thể
        if (listIndividual == null || listIndividual.isEmpty()) {
            return null; // Trả về null nếu danh sách cá thể rỗng
        }
        double T = 0;
        for (Individual instance : listIndividual) {
            T += instance.Fitness();
        }
        Random random = new Random();
        double r = random.nextDouble() * T;
        double check = 0;
        for (Individual instance : listIndividual) {
            check += instance.Fitness();
            if (check > r) {
                return instance;
            }
        }
        return null;
    }

    private ArrayList<Point> createChild(int size) {
        return new ArrayList<>(Collections.nCopies(size, null));
    }

    public void excute(int t, ArrayList<Point> map1, ArrayList<Point> map2, ArrayList<Point> child1, ArrayList<Point> child2,
             ArrayList<Point> dadSolution, ArrayList<Point> momSolution) {
        if (map1.contains(dadSolution.get(t))) {
            int post = map1.indexOf(dadSolution.get(t));
            child1.set(t, map2.get(post));
        } else {
            child1.set(t, dadSolution.get(t));
        }
        if (map2.contains(momSolution.get(t))) {
            int post = map2.indexOf(momSolution.get(t));
            child2.set(t, map1.get(post));
        } else {
            child2.set(t, momSolution.get(t));
        }
    }

    // lai anh xa tung phan 2 diem cat 
    public ArrayList<Individual> CrossoverPMX(Individual dad, Individual mom) {
        int size = dad.getSolution().size();
        ArrayList<Point> dadSolution = dad.getSolution();
        ArrayList<Point> momSolution = mom.getSolution();
        // sinh ra 2 diem cat
        Random random = new Random();
        int cutPoint1 = random.nextInt(size);
        int cutPoint2 = random.nextInt(size);

        if (cutPoint1 > cutPoint2) {
            int temp = cutPoint1;
            cutPoint1 = cutPoint2;
            cutPoint2 = temp;
        }
        // Khởi tạo các con ban đầu với các phần tử rỗng

        //System.out.println("cutPoint1: " + cutPoint1);
        //System.out.println("cutPoint2: " + cutPoint2);
        // hoan vi cac diem cat
        ArrayList<Point> child1 = createChild(size);
        ArrayList<Point> child2 = createChild(size);
        for (int i = cutPoint1; i <= cutPoint2; i++) {
            child1.set(i, momSolution.get(i));
            child2.set(i, dadSolution.get(i));
        }
        // dien ca gia tri con lai 
        List<Point> subChild1 = momSolution.subList(cutPoint1, cutPoint2 + 1);
        List<Point> subChild2 = dadSolution.subList(cutPoint1, cutPoint2 + 1);

        ArrayList<Point> subChild1copy = new ArrayList(subChild1);
        ArrayList<Point> map1 = new ArrayList(subChild1);
        ArrayList<Point> map2 = new ArrayList(subChild2);
        map1.removeAll(map2);
        map2.removeAll(subChild1copy);
        //System.out.println("subChild1: " + subChild1);
        //System.out.println("subChild2: " + subChild2);
        //System.out.println("map1: " + map1);
        //System.out.println("map2: " + map2);

        // xu li truoc pointCut1
        for (int t = 0; t < cutPoint1; t++) {
            excute(t, map1, map2, child1, child2, dadSolution, momSolution);
        }
        // xu li sau pointCut 2
        for (int t = cutPoint2 + 1; t < size; t++) {
            excute(t, map1, map2, child1, child2, dadSolution, momSolution);
        }
        // tao doi tuong tra ve
        Individual individualChild1 = new Individual(child1);
        Individual individualChild2 = new Individual(child2);
        ArrayList<Individual> individualChild = new ArrayList();
        individualChild.add(individualChild1);
        individualChild.add(individualChild2);
        return individualChild;
    }

    public Individual Mutation(Individual individual) {
        ArrayList<Point> originalSolution = individual.getSolution();
        System.out.println("Dad: " + individual.toString());
        int size = originalSolution.size();

        Random random = new Random();
        int cutPoint1 = random.nextInt(size);
        int cutPoint2 = random.nextInt(size);

        if (cutPoint1 > cutPoint2) {
            int temp = cutPoint1;
            cutPoint1 = cutPoint2;
            cutPoint2 = temp;
        }

        // Sao chép và xáo trộn đoạn con
        List<Point> sublist = new ArrayList<>(originalSolution.subList(cutPoint1, cutPoint2 + 1));
        Collections.shuffle(sublist);

        // Tạo giải pháp mới với đoạn con đã được xáo trộn
        ArrayList<Point> mutatedSolution = new ArrayList<>(originalSolution);
        for (int i = cutPoint1; i <= cutPoint2; i++) {
            mutatedSolution.set(i, sublist.get(i - cutPoint1));
        }

        // Trả về một đối tượng Individual mới với giải pháp đã đột biến
        return new Individual(mutatedSolution);
    }

    public Population Update(Population populationParent, Population populationChild) {
        ArrayList<Individual> parentList = populationParent.getPopulation();
        ArrayList<Individual> childList = populationChild.getPopulation();

        // Thêm tất cả các cá thể từ populationChild vào populationParent
        parentList.addAll(childList);

        // Cập nhật lại quần thể trong populationParent
        populationParent.setPopulation(parentList);

        return populationParent;
    }
    public Individual TournamentSelection(ArrayList<Individual> population, int tournamentSize) {
        Random random = new Random();
        ArrayList<Individual> tournament = new ArrayList<>();

        // Chọn ngẫu nhiên các cá thể vào giải đấu
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.size());
            tournament.add(population.get(randomIndex));
        }
        // Chọn cá thể có fitness cao nhất từ giải đấu
         if (!tournament.isEmpty()) {
        // Sắp xếp giải đấu theo fitness giảm dần
        Collections.sort(tournament, (a, b) -> Double.compare(b.Fitness(), a.Fitness()));

        // Chọn cá thể có fitness cao nhất từ giải đấu
        return tournament.get(0);
    } else {
        // Trong trường hợp danh sách giải đấu rỗng, trả về null hoặc làm xử lý phù hợp
        return null; // hoặc return một giá trị mặc định khác
    }
    }
}
