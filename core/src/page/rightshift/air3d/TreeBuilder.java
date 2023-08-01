package page.rightshift.air3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.LinkedList;

public class TreeBuilder {
    LinkedList<Model> treeModels;

    TreeBuilder() {
        treeModels = new LinkedList<>();
        treeModels.add(makeModel(1));
        treeModels.add(makeModel(2));
    }

    private Model makeModel(float treeType) {
        ModelBuilder builder = new ModelBuilder();
        MeshPartBuilder meshBuilder;
        builder.begin();
        meshBuilder = builder.part("trunk", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)));
        meshBuilder.cylinder(1f, 3f, 1f, 8);
        Node trunkNode = builder.node();
        trunkNode.translation.set(new Vector3(0, 3, 0));
        meshBuilder = builder.part("leaves", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)));

        if(treeType == 1) {
            meshBuilder.cone(4f, 4f, 4f, 8);
        } else {
            meshBuilder.sphere(4f, 4f, 4f, 8, 8);
        }

        return builder.end();
    }

    public Tree build() {
        Tree tree = new Tree(treeModels.get(MathUtils.random(0, 1)));
        tree.transform.setToTranslation(new Vector3(MathUtils.random(-Terrain.worldSize, Terrain.worldSize), -10,
                MathUtils.random(-Terrain.worldSize, Terrain.worldSize)));
        return tree;
    }
}
