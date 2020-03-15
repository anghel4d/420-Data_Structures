/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.AsChar;
import ca.qc.johnabbott.cs406.collections.Copyable;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Token;

/**
 * Store search information at a particular point in the terrain. Used as values in the sparse array.
 */
class Cell implements Copyable<Cell>, AsChar {

    private Color color;
    private Direction toDir;
    private Direction fromDir;

    /**
     * Create a white cell.
     */
    public Cell() {
        this(Color.WHITE);
    }

    /**
     * Create a cell
     * @param color The cell color.
     */
    public Cell(Color color) {
        this.setColor(color);
        setToDir(Direction.NONE);
        setFromDir(Direction.NONE);
    }

    @Override
    public char toChar() {
        switch (color) {
            case BLACK:
                return Token.BLACK_TOKEN.toChar();
            case GREY:
                return Token.GREY_TOKEN.toChar();
            case WHITE:
                return Token.EMPTY.toChar();
            default:
                return Token.EMPTY.toChar();
        }
    }

    @Override
    public Cell copy() {
        Cell clone = new Cell();
        clone.setColor(this.getColor());
        clone.setToDir(this.getToDir());
        return clone;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Direction getToDir() {
        return toDir;
    }

    public void setToDir(Direction toDir) {
        this.toDir = toDir;
    }

    public Direction getFromDir() {
        return fromDir;
    }

    public void setFromDir(Direction fromDir) {
        this.fromDir = fromDir;
    }
}
