package satoru.life;

class Main {
    public static void main(String[] args) {
//        var g = new Grid(100,100);
//        g.blit(50,50, Grid.GLIDER);
//        g.exportAsImages("output\\r", 10);

        var g = Grid.randomGrid(1080,1920);
        g.exportAsImages("output\\r", 1800);
    }
}
