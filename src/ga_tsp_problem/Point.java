    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package ga_tsp_problem;

    /**
     *
     * @author hp
     */
    public class Point {

        private double x;
        private double y;
        private int id;

        public Point( int id,double x, double y) {

            this.id = id;
            this.x = x;
            this.y = y;
        }
        public Point( int id) {
            this.id = id;
        }
        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public Point() {
        }

        @Override
        public String toString() {
            //return "{" + id + "} :(" + x + ',' + y+");" ;
            return id + " " ;
        }

    }
