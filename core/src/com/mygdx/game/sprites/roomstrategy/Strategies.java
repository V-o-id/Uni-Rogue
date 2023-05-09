package com.mygdx.game.sprites.roomstrategy;

public enum Strategies {

    BOTTOM_LEFT_UP, // works
    BOTTOM_RIGHT_UP, // works
    TOP_LEFT_DOWN, // works
    TOP_RIGHT_DOWN, // works

    SNAKE_END_INSIDE,
    SNAKE_END_OUTSIDE,

    BOTTOM_LEFT_STRAIGHT_UP, //works
    BOTTOM_RIGHT_STRAIGHT_UP, // works
    TOP_LEFT_STRAIGHT_DOWN, // works
    TOP_RIGHT_STRAIGHT_DOWN, // works

    BOTTOM_LEFT_HALF_IN_UP; // works


    public static RoomStrategy getStrategy(int s, int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        Strategies strategy = Strategies.values()[s];
        System.out.println("Strategy: " + strategy);
        switch (strategy) {
            case BOTTOM_LEFT_UP:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn);
            case BOTTOM_RIGHT_UP:
                return new BottomRightUp(roomsPerRow, roomsPerColumn);
            case TOP_LEFT_DOWN:
                return new TopLeftDown(roomsPerRow, roomsPerColumn);
            case TOP_RIGHT_DOWN:
                return new TopRightDown(roomsPerRow, roomsPerColumn);
            case SNAKE_END_INSIDE:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn); // todo
            case SNAKE_END_OUTSIDE:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn); // todo
            case BOTTOM_LEFT_STRAIGHT_UP:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn);
            case BOTTOM_RIGHT_STRAIGHT_UP:
                return new BottomRightStraightUp(roomsPerRow, roomsPerColumn);
            case TOP_LEFT_STRAIGHT_DOWN:
                return new TopLeftStraightDown(roomsPerRow, roomsPerColumn);
            case TOP_RIGHT_STRAIGHT_DOWN:
                return new TopRightStraightDown(roomsPerRow, roomsPerColumn);
            case BOTTOM_LEFT_HALF_IN_UP:
                return new BottomLeftHalfInUp(roomsPerRow, roomsPerColumn);
            default:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn); // default strategy
        }
    }


}
