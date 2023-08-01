package page.rightshift.air3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Terrain extends WorldObject {
    public static final float worldSize = 1500f;
    public static final int cityCount = 20;
    public static final int treeCount = 4000;

    public Terrain() {
        super(new ModelBuilder().createBox(worldSize * 2, 1f, worldSize * 2,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }
}
