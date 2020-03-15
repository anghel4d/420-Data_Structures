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

        boolean backtracked = false;

        // setup random direction generator.
        Random random = new Random();
        Generator<Direction> generator = Direction.generator();

        // track the current search location, starting at the terrain start location.
        Location currentLocation = terrain.getStart();

        // Start by trying to move UP.
        Direction previousDirection = Direction.UP;

        while(!currentLocation.equals(terrain.getGoal())) {

            // find the next direction
            Direction nextDirection = Direction.NONE;
            Location nextLocation = currentLocation.get(previousDirection);

            // change direction if we can't go in the previous direction
            if((!terrain.inTerrain(nextLocation) || terrain.isWall(nextLocation)) || memory.get(nextLocation).getColor() != Color.WHITE) {

                // check in all directions starting from UP and moving clockwise.
                for(int i = 0; i < 4; i++){

                    // Get next clockwise direction.
                    Direction tmp = Direction.getClockwise()[i];

                    // see if stepping in that direction is possible and do it!
                    nextLocation = currentLocation.get(tmp);
                    if (terrain.inTerrain(nextLocation) && !terrain.isWall(nextLocation) && memory.get(nextLocation).getColor() == Color.WHITE) {
                        previousDirection = nextDirection = tmp;
                        break;
                    }
                }

                // if no direction was found, we are stuck and leave without solution
                if(nextDirection == Direction.NONE) {
                    return;
                    /*
                    if(steps.isEmpty()){
                        return;
                    }
                    else{
                        // Get the opposite of the last direction from stack.
                        Direction last = steps.pop().opposite();

                        // Signal the Backtrack.
                        memory.get(currentLocation).setToDir(last);
                        currentLocation = currentLocation.get(last);

                        // Temporarily set previous cell to white.
                        memory.get(currentLocation).setColor(Color.WHITE);

                        backtracked = true;
                    }
                    */
                }

            }
            else{
                nextDirection = previousDirection;
            }

            // Save the direction in stack in case of backtracking
            //steps.push(nextDirection);

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
