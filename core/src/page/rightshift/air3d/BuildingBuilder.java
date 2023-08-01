package page.rightshift.air3d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;

public class BuildingBuilder {
    public Building build(Vector3 pos) {
        // get the texture
        // get texture attributes
        Texture texture = new Texture("buildingtex.png");
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        TextureAttribute attribute = TextureAttribute.createDiffuse(texture);

        // build model
        // use TextureCoordinates so we can texture it
        Model model = new ModelBuilder().createBox(
                MathUtils.random(8f, 20f),
                MathUtils.random(18f, 45f),
                MathUtils.random(12f, 24f),
                new Material(attribute),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

        // scale the UV coordinates to repeat the texture
        Matrix3 mat = new Matrix3();
        mat.scl(5);
        model.meshes.get(0).transformUV(mat);

        // create and return the building
        Building building = new Building(model);
        building.transform.setToTranslation(pos);
        return building;
    }
}
