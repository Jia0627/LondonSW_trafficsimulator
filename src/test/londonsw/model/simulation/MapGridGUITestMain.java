package londonsw.model.simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.model.simulation.components.vehicles.Ambulance;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.MapExamples;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.view.simulation.VehicleGUIDecorator;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.FileHandler;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Delete log file if exists

        String logFile = "Log_MapGridGUITestMain";

        //Map map = MapExamples.drawMap1();
        Map map = MapExamples.dratMap4();

//        Map map = MapExamples.drawTestMapExample();

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        ResizeFactor rf = ResizeFactor.getSuggestedResizeFactor(map.getWidth(), map.getHeight());
        mapGridGUIDecorator.setResizeFactor(rf);

        GridPane rootGP = mapGridGUIDecorator.drawComponents();


        /**
         *  Ambulance inherits from vehicle
         */
     /*   Lane al = map.getRandomLane();
        Ambulance a = new Ambulance(0,al);
        VehicleGUIDecorator ambulanceGUIDecorator = new VehicleGUIDecorator(a);
        ambulanceGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
        ambulanceGUIDecorator.setColor(Color.RED);
        ambulanceGUIDecorator.drawCar();
        Pane alPane = new Pane();
        alPane.getChildren().add(ambulanceGUIDecorator.getRectangle());
        //sp.getChildren().add(alPane);
        ambulanceGUIDecorator.setVehicleState(1);*/




        /**
         * We would have a button to spawn an ambulance: single click deploys the ambulance and double click removes it.
         * Button button = new Button("spawn ambulance"); button.setOnMouseClicked(event -> {do some action})
         * As we don't have the buttons yet, stack pane is used to demonstrate
         */
      /*  sp.setOnMouseClicked(event -> {

            switch (event.getClickCount()){
                case 1:
                    sp.getChildren().add(alPane);
                break;

                case 2:
                    sp.getChildren().remove(alPane);
                break;

            }

        });
*/

        // System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());

        StackPane sp = new StackPane();
        sp.getChildren().add(rootGP);

        Car testCar;
        int c=0;
        for (int i=0; i<20; i++){
            testCar = generateCar(map,mapGridGUIDecorator,sp);
            if (testCar!=null)
                c++;}
        System.out.println("Number of cars is "+ c);

        /**
         * We can now use a single button to spawn and un-spawn ambulance.
         * We can also remove it from simulation
         */

        /*
        sp.setOnMouseClicked(event -> {

                    ArrayList subscribers = Ticker.getSubscribers();

            Boolean flag = true;

            for(Object obj:subscribers)
            {
                if(obj instanceof Ambulance == true)
                {
                    //there is an ambulance but we must check if all are unsuscribed
                    Ambulance a = (Ambulance) obj;
                    if(!a.isUnsubscribed())
                    {
                        flag = false;
                    }
                }
            }

            if(flag)
            {
                generateAmbulance(map,mapGridGUIDecorator,sp);
            }


        });
        */



        Scene scene = new Scene(sp);
        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setResizable(false);

        Log log = new Log(logFile);

        //primaryStage.setFullScreen(true);
    }

    /**
     *Re-used rawan method to generate new ambulances...
     * @param map
     * @param mapGridGUIDecorator
     * @param sp
     * @return
     */
    public Ambulance generateAmbulance(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp){
        Lane AmbLane = map.getRandomLane();

        if (AmbLane!=null && (!AmbLane.isFull())){
            for (int x = 0; x < map.getRoads().size(); x++) {
                for (int y = 0; y < map.getRoads().get(x).getNumberLanes(); y++) {
                    AmbLane=map.getRandomLane();
                    for (int z = 0; z < AmbLane.getLength(); z++) {
                        if (AmbLane.isCellEmpty(z)) {
                            Ambulance A  = new Ambulance(z, AmbLane);
                            VehicleGUIDecorator ambulanceGUIDecorator = new VehicleGUIDecorator(A);
                            ambulanceGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                            ambulanceGUIDecorator.setColor(Color.RED);
                            ambulanceGUIDecorator.drawCar();
                            Pane alPane = new Pane();
                            alPane.getChildren().add(ambulanceGUIDecorator.getRectangle());
                            ambulanceGUIDecorator.setPane(alPane);
                            sp.getChildren().add(alPane);
                            ambulanceGUIDecorator.setVehicleState(1);
                            return A;

                        }
                    }

                }

            }

        }
        return null;
    }


    public Car generateCar(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp){

        Lane L1 =map.getRandomLane();
        //Lane L1 =map.getRoads().get(0).getLanes().get(1);

        Lane L2;

        if(L1!=null)
            for (int a=0; a<map.getRoads().size();a++)
            {
                for(int b=0; b<map.getRoads().get(a).getNumberLanes();b++)
                {
                    L2= map.getRoads().get(a).getLanes().get(b);
                    for (int i=0; i<L1.getLength();i++) {
                        if (L1.isCellEmpty(i)) {
                            Car C1 = new Car(i, L1);
                            //C1.setVehicleBehavior(VehicleBehavior.AGGRESSIVE);
                            VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);
                            vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                            vehicleGUIDecorator.drawCar();
                            Pane carPane = new Pane();

                            carPane.setPickOnBounds(false); //allows me to click intersections

                            carPane.getChildren().add(vehicleGUIDecorator.getRectangle());
                            //carPane.getChildren().add(vehicleGUIDecorator.getGroup());
                            sp.getChildren().add(carPane);
                            vehicleGUIDecorator.setPane(carPane);
                            vehicleGUIDecorator.setVehicleState(1);
                            //System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
                            return C1;

                        }
                    }
                }
            }

        return null;

    }

    public static void main(String[] args) {
        launch(args);
    }
}