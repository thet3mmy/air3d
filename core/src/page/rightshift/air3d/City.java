package page.rightshift.air3d;

import com.badlogic.gdx.math.Vector3;

public class City {
    public Vector3 center;
    public int numBuildings;
    public float size;
    private BuildingBuilder builder;

    City(Vector3 c, int n, float s) {
        center = c;
        builder = new BuildingBuilder();
        numBuildings = n;
        size = s;
    }

    public void build() {
        for(int b = 0; b < numBuildings; b++) {
            Vector3 offset = GameUtils.newRandomVector3XZ(-size, size);

            Vector3 pos = center.cpy().add(offset);
            Building building = builder.build(pos);
            Game.objects.add(building);
        }
    }
}
