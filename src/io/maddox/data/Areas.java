package io.maddox.data;

import org.powbot.api.Area;
import org.powbot.api.Tile;

public class Areas {
    public static final Area LUMBRIDGE = new Area(new Tile(3217, 3222, 0),new Tile(3226, 3214, 0));
    public static final Area DYEHOUSE = new Area
            (new Tile(3363, 3000, 0),
            new Tile(3365, 3003, 0));
    public static final Area menaphiteHouse = new Area
            (new Tile(3344, 2956, 0),
                    new Tile(3340, 2953, 0));
    public static final Area menaphiteArea = new Area
            (new Tile(3339, 2957, 0),
                    new Tile(3347, 2951, 0));
    public static final Area DYEHOUSE2 = new Area
            (new Tile(3353, 3006, 0),
                    new Tile(3369, 2996, 0));
    public static final Area NorthZone = new Area(new Tile(3352, 3006, 0),new Tile(3368, 2996, 0));
    public static final Area SouthZone = new Area(new Tile(3333, 2959, 0),new Tile(3352, 2947, 0));
    public static final Area DYEHOUSEupstairs = new Area(new Tile(3353, 3006, 1),new Tile(3368, 2998, 1));
    public static final Area menaUpstairs = new Area(new Tile(3351, 2960, 1),new Tile(3356, 2954, 1));
    public static final Area menaDownstairs = new Area(new Tile(3351, 2960, 0),new Tile(3356, 2954, 0));
    public static final Area dyeupstairs = new Area(new Tile(3353, 3006, 1),new Tile(3368, 2998, 1));

    //Tiles

    public static final Tile dyedownstairs = new Tile(3364, 3002, 0);
    public static final Tile menaDownstairstile = new Tile(3353, 2961, 0);
    public static final Tile NoteManager = new Tile(3359, 2989, 0);
    public static final Tile Curtain = new Tile(3364, 2999, 0);
    public static final Tile dyeLadderdownstairs = new Tile(3364, 3003, 0);
    public static final Tile dyeLadderupstairs = new Tile(3364, 3003, 1);
    public static final Tile menaCurtain = new Tile(3345, 2955, 0);
    public static final Tile dyetoLure = new Tile(3364, 3002, 0);
    public static final Tile menatoLure = new Tile(3343, 2954, 0);
    public static final Tile menaStairs = new Tile(3353, 2958, 0);
    public static final Tile menaUpStairstile = new Tile(3353, 2958, 1);

    public static final Tile outsidebanHouse = new Tile(3364, 2997, 0);

    public static final Tile outsideMenaHouse = new Tile(3347, 2956, 0);
}
