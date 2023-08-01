package page.rightshift.air3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.text.DecimalFormat;

public class GameUI {
    public Stage stage;
    public Skin skin;
    public Label label;
    public Label respawnLabel;

    GameUI() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        label = new Label("", skin);
        label.setPosition(0, Gdx.graphics.getHeight() - 16);

        respawnLabel = new Label("Respawning in 3 seconds", skin);
        respawnLabel.setPosition(-1000, -1000);

        stage.addActor(label);
        stage.addActor(respawnLabel);
    }

    public String formatForText(float f) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(0);

        if(f == 0) {
            return "0";
        } else {
            return format.format(f);
        }
    }

    public void showRespawnText() {
        respawnLabel.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f);
    }

    public void hideRespawnText() {
        respawnLabel.setPosition(-1000, -1000);
    }

    public void update() {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(0);
        Quaternion q = Game.plane.transform.getRotation(new Quaternion());
        label.setText(" fps: " + Gdx.graphics.getFramesPerSecond() +
                " yaw: " + formatForText(q.getYaw()) +
                " pitch: " + formatForText(q.getPitch()) +
                " roll: " + formatForText(q.getRoll()) +
                " objects: " + Game.objects.size +
                " numProcessed: " + Game.numProcessed);

        respawnLabel.setText("Respawning in " + (int)(((Game.plane.respawnTime - Game.plane.tick) / 60) + 1));
    }

    public void draw() {
        stage.act();
        stage.draw();
    }
}
