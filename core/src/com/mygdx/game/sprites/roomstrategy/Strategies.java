package com.mygdx.game.sprites.roomstrategy;

public enum Strategies {

    BOTTOM_LEFT_UP, // works
    BOTTOM_RIGHT_UP,
    TOP_LEFT_DOWN,
    TOP_RIGHT_DOWN,

    SNAKE_END_INSIDE,
    SNAKE_END_OUTSIDE,

    BOTTOM_LEFT_STRAIGHT_UP, //works
    BOTTOM_RIGHT_STRAIGHT_UP,
    TOP_LEFT_STRAIGHT_DOWN, // works
    TOP_RIGHT_STRAIGHT_DOWN;


     public static RoomStrategy getStrategy(int s, int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
         Strategies strategy = Strategies.values()[s];
         System.out.println("Strategy: " + strategy);
        switch (strategy) {
            case BOTTOM_LEFT_UP:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn);
            case BOTTOM_RIGHT_UP:
                return new TopLeftStraightDown(roomsPerRow, roomsPerColumn); //todo
            case TOP_LEFT_DOWN:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn); // todo
            case TOP_RIGHT_DOWN:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn); // todo
            case SNAKE_END_INSIDE:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn); // todo
            case SNAKE_END_OUTSIDE:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn); // todo
            case BOTTOM_LEFT_STRAIGHT_UP:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn);
            case BOTTOM_RIGHT_STRAIGHT_UP:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn); // todo
            case TOP_LEFT_STRAIGHT_DOWN:
                return new TopLeftStraightDown(roomsPerRow, roomsPerColumn);
            case TOP_RIGHT_STRAIGHT_DOWN:
                return new BottomLeftStraightUp(roomsPerRow, roomsPerColumn); // todo
            default:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn); // default strategy
        }
    }


}
