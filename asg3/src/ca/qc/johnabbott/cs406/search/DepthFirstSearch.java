/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Generator;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;
import ca.qc.johnabbott.cs406.collections.Stack;

import java.util.Random;
public class DepthFirstSearch implements Search {

    // records where we've been and what steps we've taken.
    private SparseArray<Cell> memory;

    // record the steps we've taken
    public Stack<Direction> steps;

    // for tracking the "traversable" solution.
    private Location solution;

    // the terrain we're searching in.
    private Terrain terrain;

    /**
     * Create a new Random search.
     */
    public DepthFirstSearch() {
    }

    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;

        // track locations we've been to using our terrain "memory"
        Cell defaultCell = new Cell();
        memory = new SparseArray<>(defaultCell);
        steps = new Stack<>();

        // track the current search location, starting at the terrain start location.
        Location currentLocation = terrain.getStart();

        // Start off the previous direction as up.
        Direction previous = Direction.UP;

        // Keep trying until the goal is reached or the cursor is stuck.
        while(!currentLocation.equals(terrain.getGoal())) {

            // find the next direction
            Direction nextDirection = Direction.NONE;
            Location nextLocation = currentLocation.get(previous);

            // Keep going in one direction until there is nowhere else to go, then change direction.
            if((!terrain.inTerrain(nextLocation) || terrain.isWall(nextLocation)) || memory.get(nextLocation).getColor() != Color.WHITE) {
                // Check all directions starting from UP and moving clockwise.
                for(int i = 0; i < Direction.getClockwise().length; i++){

                    // Get next clockwise direction, or the last valid direction if there was one.
                    //Direction tmp = steps.isEmpty() ? Direction.getClockwise()[i] : steps.peek();
                    Direction tmp = Direction.getClockwise()[i];

                    // see if stepping in that direction is possible and do it!
                    nextLocation = currentLocation.get(tmp);
                    if (terrain.inTerrain(nextLocation) && !terrain.isWall(nextLocation) && memory.get(nextLocation).getColor() == Color.WHITE) {
                        previous = nextDirection = tmp;
                        steps.push(nextDirection);
                        break;
                    }
                }

                // if no direction was found, we are stuck and leave without solution
                if(nextDirection == Direction.NONE) {
                    // Try to backtrack if possible
                    if(!steps.isEmpty()) {
                        nextDirection = steps.pop().opposite();
                    }
                    else {
                        return;
                    }
                }
            }
            else {
                nextDirection = previous;
                steps.push(nextDirection);
            }

            // record the step we've taken to memory to recreate the solution in the later traversal.
            memory.get(currentLocation).setToDir(nextDirection);

            // step
            currentLocation = currentLocation.get(nextDirection);

            // record that we've been here
            memory.get(currentLocation).setColor(Color.BLACK);

            System.out.println(memory);
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
