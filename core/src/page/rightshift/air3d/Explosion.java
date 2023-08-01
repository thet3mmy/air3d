package page.rightshift.air3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Explosion extends GameObject {
    public long ticks;

    public Explosion() {
        super(new ModelBuilder().createSphere(16f, 16f, 16f, 8, 8,
                new Material(ColorAttribute.createDiffuse(Color.ORANGE)),
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position));
    }

    public Explosion init(Vector3 centerPos) {
        transform.setToTranslation(centerPos);
        return this;
    }

    @Override
    public void think() {
        ticks++;
        if(ticks > Plane.respawnDuration / 2f) {
            delete();
        }
    }
}
