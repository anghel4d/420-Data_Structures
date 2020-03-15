/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.terrain;

import java.util.Random;

/**
 * <p>The directions used to move position.</p>

 * @author Ian Clement
 */
public enum Direction {
    NONE, UP, DOWN, LEFT, RIGHT;

    private static Direction[] clockwise;
    private static Direction[] counterClockwise;

    static {
        clockwise = new Direction[]{UP, RIGHT, DOWN, LEFT};
        counterClockwise = new Direction[] { LEFT, DOWN, RIGHT, UP };
    }

    /**
     * Generates random directions.
     */
    private static class DirectionGenerator implements Generator<Direction> {

        @Override
        public Direction generate(Random random) {
            return clockwise[random.nextInt(clockwise.length)];
        }
    }

    /**
     * Get a random direction generator.
     * @return
     */
    public static Generator<Direction> generator() {
        return new DirectionGenerator();
    }

    /**
     * Get directions clockwise from UP.
     * @return
     */
    public static Direction[] getClockwise() {
        return clockwise;
    }

    /**
     * Get next direction clockwise from input parameter.
     * @return
     */
    public static Direction getClockwise(Direction direction) {
        return clockwise[(direction.ordinal() + 1) % clockwise.length];
    }

    /**
     * Get directions counterclockwise from LEFT.
     * @return
     */
    public static Direction[] getCounterClockwise() {
        return counterClockwise;
    }

    /**
     * Get next direction counterclockwise from input parameter.
     * @return
     */
    public static Direction getCounterClockwise(Direction direction) {
        return counterClockwise[(direction.ordinal() + 1) % counterClockwise.length];
    }

    /**
     * Get the opposite direction of the current direction.
     * @return
     */
    public Direction opposite() {
        switch(this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return NONE;
    }
}
