/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.collections.Queue;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;


public class BreadthFirstSearch implements Search {
    /** Fields **/
    // records where we've been and what steps we've taken.
    private SparseArray<Cell> memory;

    // a queue of grey cells to be visited
    public Queue<Location> greys;

    // for tracking the "traversable" solution.
    private Location solution;

    // the terrain we're searching in.
    private Terrain terrain;

    /** Constructor **/
    public BreadthFirstSearch() {
        greys = new Queue<>();
    }

    /** Methods **/
    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;

        // track locations we've been to using our terrain "memory"
        Cell defaultCell = new Cell();
        defaultCell.setColor(Color.WHITE);
        memory = new SparseArray<>(defaultCell);

        // starting at the terrain start location.
        Location currentLocation = terrain.getStart();
        greys.enqueue(currentLocation);

        while(!greys.isEmpty()) {
            // 1. Color the current cell BLACK
            currentLocation = greys.dequeue();
            memory.get(currentLocation).setColor(Color.BLACK);

            // Check all directions starting from UP and moving clockwise.
            for(int i = 0; i < Direction.getClockwise().length; i++){
                Direction nextDirection = Direction.getClockwise()[i];

                // see if stepping in that direction is possible and visit it.
                Location nextLocation = currentLocation.get(nextDirection);
                if (terrain.inTerrain(nextLocation) && !terrain.isWall(nextLocation)) {
                    if(memory.get(nextLocation).getColor() == Color.WHITE){
                        // 4. Move in this direction, color it grey, and immediately backtrack to current cell.

                        memory.get(nextLocation).setColor(Color.GREY);
                        memory.get(nextLocation).setFromDir(nextDirection.opposite());
                        greys.enqueue(nextLocation);
                    }
                }
            }

            System.out.println(memory);

            if(currentLocation.equals(terrain.getGoal())){
                TracePath();
                return;
            }
        }
    }

    private void TracePath(){
        Location current = terrain.getGoal();
        while(!current.equals(terrain.getStart())){
            Direction dir = memory.get(current).getFromDir();
            current = current.get(dir);
            memory.get(current).setToDir(dir.opposite());
        }
    }

    @Override
    public void reset() {
        // start the traversal of our path at the terrain's start.
        solution = terrain.getStart();
    }

    @Override
    public Direction next() {
        // recall the direction at this location, move to the corresponding location and return it.
        Direction direction = memory.get(solution).getToDir();
        solution = solution.get(direction);
        return direction;
    }

    @Override
    public boolean hasNext() {
        // we're only done when we get to the terrain goal.
        return !solution.equals(terrain.getGoal());
    }
}
