package io.maddox.data;

import org.powbot.api.Area;
import org.powbot.api.Tile;

public class Areas {
    public static final Area LUMBRIDGE = new Area(new Tile(3217, 3222, 0),new Tile(3226, 3214, 0));
    public static final Area DYEHOUSE = new Area
            (new Tile(3363, 3003, 0),
            new Tile(3365, 2999, 0));
    public static final Area DYEHOUSE2 = new Area
            (new Tile(3363, 3003, 0),
                    new Tile(3365, 3000, 0));
    //Area area = new Area(3361, 3006, 3367, 2998);
    public static final Tile NoteManager = new Tile(3359, 2989, 0);
    public static final Area Curtain = new Area(new Tile(3364, 2999, 0));
    public static final Area NorthZone = new Area(new Tile(3352, 3006, 0),new Tile(3368, 2996, 0));
    public static final Area DYEHOUSEupstairs = new Area(new Tile(3353, 3006, 1),new Tile(3368, 2998, 1));

    //Tiles
    public static final Tile toLure = new Tile(3364, 3002, 0);
    public static final Tile outsideHouse = new Tile(3364, 2997, 0);
}
