package page.rightshift.air3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Game extends ApplicationAdapter {
	public static PerspectiveCamera camera;
	public ModelBatch batch;
	public Environment environment;
	public static Terrain terrain;
	public static Array<GameObject> objects;
	public static Plane plane;
	public static GameUI gameUI;
	public static int numProcessed;

	@Override
	public void create () {
		camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(new Vector3(0, 4, 10));
		camera.near = 0.1f;
		camera.far = 999999f;
		camera.update();

		batch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		objects = new Array<>();

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.8f, 1f);
		gameUI = new GameUI();

		plane = new Plane();
		plane.transform.setToTranslation(new Vector3(0, 50, 0));
		plane.materials.get(0).set(ColorAttribute.createDiffuse(Color.RED));
		objects.add(plane);

		buildScene();
	}

	private void buildScene() {
		terrain = new Terrain();
		terrain.transform.setToTranslation(new Vector3(0, -12, 0));
		objects.add(terrain);

		TreeBuilder treeBuilder = new TreeBuilder();
		for(int i = 0; i < Terrain.treeCount; i++)
			objects.add(treeBuilder.build());

		for(int i = 0; i < Terrain.cityCount; i++)
			new City(GameUtils.newRandomVector3XZ(-Terrain.worldSize + 200, Terrain.worldSize - 200), 75, 200).build();
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		camera.update();
		controls();

		numProcessed = 0;

		for(GameObject o: new Array<>(objects)) {
			o.think();
			float distance = o.transform.getTranslation(new Vector3()).dst(
					plane.transform.getTranslation(new Vector3()));

			if(distance < 128f || o == terrain) {
				numProcessed++;
				if (o.getBoundingBox().intersects(plane.getBoundingBox())) {
					if (!o.equals(plane)) {
						plane.kill();
					}
				}
			}
		}

		batch.begin(camera);
		batch.render(objects, environment);
		batch.end();

		gameUI.update();
		gameUI.draw();
	}

	public void controls() {
		plane.controls();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for(GameObject o: objects) {
			try {
				o.dispose();
			} catch (Exception ignored) {}
		}
	}
}
