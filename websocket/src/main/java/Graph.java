import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
import java.util.Random;

public class Graph {
    LinkedList<LinkedList<Integer>> matrix = new LinkedList<>();
    int size;
    public final int INF = Integer.MAX_VALUE;

    Graph(int[][] coordinates) {
        Random random = new Random();

        this.size = coordinates.length;
        for (int i = 0; i < this.size; i++){
            matrix.add(new LinkedList<>());
            for (int j = 0; j < this.size; j++)
                matrix.get(i).add(INF);
        }


        for (int i = 0; i < this.size; i++){
            for (int j = i + 1; j < this.size; j++){
                if (random.nextBoolean() == true) {
                    int dx = (coordinates[i][0] - coordinates[j][0]) * (coordinates[i][0] - coordinates[j][0]);
                    int dy = (coordinates[i][1] - coordinates[j][1]) * (coordinates[i][1] - coordinates[j][1]);
                    int dz = (coordinates[i][2] - coordinates[j][2]) * (coordinates[i][0] - coordinates[j][2]);
                    int len = (int)Math.sqrt(dx + dy + dz);
                    matrix.get(i).set(j, len);
                    matrix.get(j).set(i, len);
                }
            }
        }

        System.out.println(matrix);
    }

    public List<Integer> getPath() {
        Random random = new Random();
        int v1 = 0, v2 = 0;
        while(v1 == v2){
            v1 = random.nextInt(this.size);
            v2 = random.nextInt(this.size);
        }

        System.out.println(v1 + " " + v2);
        return dijkstra(v1, v2);
    }

    public List<List<Integer>> getEdges(){
        List<List<Integer>> edges = new LinkedList<>();
        for (int i = 0; i < this.size; i++){
            for (int j = i; j < this.size; j++){
                if (matrix.get(i).get(j) != INF) {
                    List<Integer> e = new LinkedList<>();
                    e.add(i);
                    e.add(j);
                    edges.add(e);
                }
            }
        }

        return edges;
    }

    public LinkedList<Integer> dijkstra(int v1, int v2){
        LinkedList<Boolean> used = new LinkedList<>();
        LinkedList<Integer> dist = new LinkedList<>();
        LinkedList<LinkedList<Integer>> path = new LinkedList<LinkedList<Integer>>();
        for (int i = 0; i < this.size; i++){
            used.add(false);
            dist.add(INF);
            path.add(new LinkedList<>());
        }
        dist.set(v1, 0);
        path.get(v1).add(v1);
        while (true){
            int v = -1;
            for (int nv = 0; nv < this.size; nv++)
                if (!used.get(nv) && dist.get(nv) < INF && (v == -1 || dist.get(v) > dist.get(nv)))
                    v = nv;
            if (v == -1) break;
            used.set(v, true);
            for (int nv = 0; nv < this.size; nv++)
                if (!used.get(nv) && matrix.get(v).get(nv) < INF)
                    if (dist.get(v) + matrix.get(v).get(nv) < dist.get(nv)) {
                        dist.set(nv, dist.get(v) + matrix.get(v).get(nv));
                        path.get(nv).clear();
                        path.get(nv).addAll(path.get(v));
                        path.get(nv).add(nv);
                    }
        }
        return path.get(v2);
    }
}
