import com.sun.javafx.geom.Vec3f;

import java.util.List;

public class ResponseData {

    private List<Integer> path;
    private int[][] coord;
    private List<List<Integer>> edges;

    ResponseData() { }

    ResponseData(List<List<Integer>> edges, int[][] coord, List<Integer> path) {
        this.edges = edges;
        this.coord = coord;
        this.path = path;
    }

    ResponseData(int[][] coord) {
        this.coord = coord;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int[][] getCoord() {
        return coord;
    }

    public List<List<Integer>> getEdges() {
        return edges;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public void setCoord(int[][] coord) {
        this.coord = coord;
    }

    public void setEdges(List<List<Integer>> edges) {
        this.edges = edges;
    }
}
