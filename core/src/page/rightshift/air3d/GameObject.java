package page.rightshift.air3d;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameObject extends ModelInstance implements Disposable {
    public GameObject(Model model) {
        super(model);
    }

    public void think() {}
    public BoundingBox getBoundingBox() {
        BoundingBox box = new BoundingBox();
        model.calculateBoundingBox(box);
        box.mul(transform);
        return box;
    }
    public void delete() {
        dispose();
        Game.objects.removeIndex(Game.objects.indexOf(this, true));
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
