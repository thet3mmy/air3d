package page.rightshift.air3d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class GameUtils {
    public static Vector3 newRandomVector3XZ(float min, float max) {
        return new Vector3(MathUtils.random(min, max), 0, MathUtils.random(min, max));
    }
}
