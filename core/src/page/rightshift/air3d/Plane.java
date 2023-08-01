package page.rightshift.air3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;

public class Plane extends GameObject {
    public static final float speed = 25f;
    public static final float pitchSpeed = 1f;
    public static final float rollSpeed = 2f;
    public static final long respawnDuration = 180;

    private boolean respawnConfirmed = false;
    public boolean living = true;
    public long tick = 0;
    public long respawnTime = 999999999;
    private long respawnConfirmationResetTime = 99999999;
    Vector3 direction;

    // super(new ModelBuilder().createBox(6f, 1f, 6f,
    //                new Material(ColorAttribute.createDiffuse(Color.RED)),
    //                    VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position));

    public Plane() {
        super(new ObjLoader().loadModel(Gdx.files.internal("plane.obj")));
    }

    private void move() {
        transform.trn(direction.scl(speed).scl(Gdx.graphics.getDeltaTime()));
    }

    public void controls() {
        boolean inputProcessed = false;

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            transform.rotate(Vector3.Z, -rollSpeed);
            inputProcessed = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            transform.rotate(Vector3.Z, rollSpeed);
            inputProcessed = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            transform.rotate(Vector3.X, -pitchSpeed);
            inputProcessed = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            transform.rotate(Vector3.X, pitchSpeed);
            inputProcessed = true;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            if(respawnConfirmed) {
                kill();
                inputProcessed = true;
                respawnConfirmed = false;
            } else {
                respawnConfirmed = true;
                respawnConfirmationResetTime = tick + 60;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Ray ray = new Ray();
            ray.origin.set(transform.getTranslation(new Vector3()));
            ray.direction.set(direction);
            Game.objects.add(new Bullet().init(ray));
        }

        if(!inputProcessed) {
            Quaternion q = transform.getRotation(new Quaternion());
            if(q.getRoll() > 1) {
                transform.rotate(Vector3.Z, -rollSpeed);
            } else if (q.getRoll() < -1) {
                transform.rotate(Vector3.Z, rollSpeed);
            }
        }
    }

    public void kill() {
        if(!living)
            return;

        respawnTime = tick + 180;
        living = false;
        Game.objects.add(new Explosion().init(transform.getTranslation(new Vector3())));
        transform.setToTranslation(new Vector3(9999999, 9999999,9999999));

        Game.gameUI.showRespawnText();
    }

    @Override
    public void think() {
        tick++;

        direction = new Vector3(0,0,1).rot(transform);
        Vector3 initialPosition = transform.getTranslation(new Vector3()).cpy();

        if(living) {
            Vector3 cameraPosition = initialPosition.cpy();
            cameraPosition.add(direction.cpy().scl(-18f).add(new Vector3(0, 6, 0)));
            Game.camera.position.set(cameraPosition);
            Game.camera.direction.set(direction);
            move();

            if(tick > respawnConfirmationResetTime) {
                respawnConfirmationResetTime = 99999999;
                respawnConfirmed = false;
            }
        } else {
            if(tick > respawnTime) {
                living = true;
                transform.setToTranslation(new Vector3(0, 50, 0));
                respawnTime = 999999999;
                Game.gameUI.hideRespawnText();
            }
        }
    }

    @Override
    public BoundingBox getBoundingBox() {
        BoundingBox box = new BoundingBox();
        box.min.set(new Vector3(-4f, -0.5f, -4f));
        box.max.set(new Vector3(4f, 0.5f, 4f));
        box.mul(transform);
        return box;
    }
}
