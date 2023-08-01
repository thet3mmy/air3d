package page.rightshift.air3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class Bullet extends GameObject {
    Ray ray;
    float dist = 50f;

    public Bullet() {
        super(new ModelBuilder().createSphere(0.5f, 0.5f, 0.5f, 8, 8,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    public Bullet init(Ray r) {
        ray = r;
        return this;
    }

    @Override
    public void think() {
        dist += 2f;
        transform.setToTranslation(ray.getEndPoint(new Vector3(), dist));

        if(dist > 500f) {
            Game.objects.removeIndex(Game.objects.indexOf(this, true));
        }
    }
}
