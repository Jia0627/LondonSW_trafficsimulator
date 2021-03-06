package londonsw.controller;


import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.IntersectionDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class IntersectionController {
    private static Map<Intersection,IntersectionDecorator> intersections = new HashMap<Intersection,IntersectionDecorator>();
    private static ArrayList<Intersection> allIntersections = new ArrayList<>();

    /**
     * Prevents instantiation of this class
     */
    protected IntersectionController() {  }

    /**
     * Register an instance of an Intersection to and IntersectionDecorator, and adds the Intersection to a list to keep
     * track of
     * @param i the instance of Intersection to register
     * @param gui the instance of IntersectionDecorator for that intersection
     */
    public static void addIntersectionAndDecoratorPair(Intersection i, IntersectionDecorator gui) {
        intersections.put(i,gui);
        allIntersections.add(i);
    }

    /**
     * Retrieve the decorator for that specific Intersection
     * @param i the Intersection to get the decorator for
     * @return the decorator associated with that Intersection
     */
    public static IntersectionDecorator getIntersectionDecoratorForIntersection(Intersection i) {
        return intersections.get(i);
    }

    /**
     * Gets all Intersections in the system
     * @return ArrayList of all Intersections in the system
     */
    public ArrayList<Intersection> getAllIntersections() {
        return allIntersections;
    }


    /**
     * This method is used for determining which vehicle will turn first in an intersection. By generating priorities,
     * this will prevent cars from driving into each other in intersections.
     * @param intersection the intersection where the vehicles are
     * @param vehicles array list of vehicles with priorities set
     * @return true if this method succeeds (always true)
     * @throws Exception
     */
    public static boolean vehicleTurnFirst(Intersection intersection, ArrayList<Vehicle> vehicles) throws Exception {
        ArrayList<Integer>  randomPriority = (ArrayList < Integer>) intersection.generateRandom().clone();
        intersection.vehicleTurnFirst(intersection.giveVehiclePriorities(randomPriority));

        return true;
    }


}
