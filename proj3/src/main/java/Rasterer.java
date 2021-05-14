import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    double stdlonDPP;

    public Rasterer() {
        // YOUR CODE HERE
        stdlonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double ullon = params.get("ullon"), ullat = params.get("ullat"),
                lrlon = params.get("lrlon"), lrlat = params.get("lrlat"),
                height = params.get("h"), width = params.get("w");
        if (lrlat >= ullat || lrlon <= ullon
                || lrlat >= MapServer.ROOT_ULLAT || lrlon <= MapServer.ROOT_ULLON
                || ullat <= MapServer.ROOT_LRLAT || ullon >= MapServer.ROOT_LRLON) {
            results.put("query_success", false);
            System.out.println(results);
            return results;
        }
        double lonDPP = (lrlon - ullon) / width;
        System.out.println("lonDPP:" + lonDPP);
        System.out.println("stdlonDPP:" + stdlonDPP);
        int depth = 0;
        while (lonDPP < stdlonDPP && depth < 7) {
            depth += 1;
            lonDPP *= 2;
        }
        int x1 = getX(depth, ullon), y1 = getY(depth, ullat),
                x2 = getX(depth, lrlon), y2 = getY(depth, lrlat);
        String[][] files = new String[y2 - y1 + 1][x2 - x1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                files[j - y1][i - x1] = "d" + depth + "_x" + i + "_y" + j + ".png";
            }
        }
        for (int i = 0; i < files.length; i++) {
            for (int j = 0; j < files[0].length; j++) {
                System.out.print(files[i][j] + " ");
            }
            System.out.print("\n");
        }
        results.put("render_grid", files);
        results.put("raster_ul_lon", getullon(depth, x1));
        results.put("raster_ul_lat", getullat(depth, y1));
        results.put("raster_lr_lon", getlrlon(depth, x2));
        results.put("raster_lr_lat", getlrlat(depth, y2));
        results.put("depth", depth);
        results.put("query_success", true);
        System.out.println(results);
        return results;
    }
    private int getX(int depth, double lon) {
        int t = 0;
        double inteval = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (1 << depth);
        double curr = MapServer.ROOT_ULLON;
        while (curr < lon && curr <= MapServer.ROOT_LRLON - inteval) {
            curr += inteval;
            t += 1;
        }
        return Math.max(t - 1, 0);
    }
    private int getY(int depth, double lat) {
        int t = 0;
        double inteval = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (1 << depth);
        double curr = MapServer.ROOT_ULLAT;
        while (curr > lat && curr >= MapServer.ROOT_LRLAT + inteval) {
            curr -= inteval;
            t += 1;
        }
        return Math.max(t - 1, 0);
    }
    private double getullat(int depth, int y) {
        double inteval = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (1 << depth);
        return MapServer.ROOT_ULLAT - y * inteval;
    }
    private double getullon(int depth, int x) {
        double inteval = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (1 << depth);
        return MapServer.ROOT_ULLON + x * inteval;
    }
    private double getlrlat(int depth, int y) {
        double inteval = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (1 << depth);
        return MapServer.ROOT_ULLAT - (y + 1) * inteval;
    }
    private double getlrlon(int depth, int x) {
        double inteval = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (1 << depth);
        return MapServer.ROOT_ULLON + (x + 1) * inteval;
    }
}
