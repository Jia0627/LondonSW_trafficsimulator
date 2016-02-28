package londonsw.view.simulation;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.*;

/**
 * Created by felix on 25/02/2016.
 */
public class MapGridGUIDecorator extends MapGridDecorator {


    public MapGridGUIDecorator(MapGrid decoratedMapGrid) {
        super(decoratedMapGrid);
    }

    private ResizeFactor resizeFactor;


    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public GridPane drawComponents() throws Exception {
        GridPane rootGP = new GridPane();
        Pane roadStackPane;

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {

                Component current = this.getGrid()[y][x];

                if (current instanceof Road) {

                    RoadGUIDecorator roadGUIDecorator = new RoadGUIDecorator((Road) current);

                    roadGUIDecorator.setResizeFactor(this.getResizeFactor().getResizeX(),this.getResizeFactor().getResizeY());

                    roadStackPane = roadGUIDecorator.drawRoad(roadGUIDecorator.runsVertically() ? MapDirection.NORTH : MapDirection.EAST);  //TODO change logic

                } else if (current instanceof Intersection) {
                    LayoutGUI intersectionGUI = new LayoutGUI();

                    intersectionGUI.setHeight(this.getHeight());
                    intersectionGUI.setWidth(this.getWidth());
                    intersectionGUI.setResizeFactor(this.getResizeFactor().getResizeX(),this.getResizeFactor().getResizeY());

                    roadStackPane = intersectionGUI.drawIntersection();
                } else {

                    LayoutGUI grassGUI = new LayoutGUI();

                    grassGUI.setHeight(this.getHeight());
                    grassGUI.setWidth(this.getWidth());
                    grassGUI.setResizeFactor(this.getResizeFactor().getResizeX(),this.getResizeFactor().getResizeY());

                    roadStackPane = grassGUI.drawGrass();
                }

                rootGP.setRowIndex(roadStackPane, y);
                rootGP.setColumnIndex(roadStackPane, x);

                rootGP.getChildren().add(roadStackPane);

            }
        }

        rootGP.setGridLinesVisible(true);

        return rootGP;
    }

}
