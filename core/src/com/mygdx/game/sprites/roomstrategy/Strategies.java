package com.mygdx.game.sprites.roomstrategy;

/**
 * Enum for the different strategies
 * This enum will be used to choose a strategy
 * This enum will also be used to get a strategy
 * This enum can also be used to get the number of strategies
 */

public enum Strategies {

    BOTTOM_LEFT_UP,
    BOTTOM_RIGHT_UP,
    TOP_LEFT_DOWN,
    TOP_RIGHT_DOWN,

    BOTTOM_LEFT_STRAIGHT_UP,
    BOTTOM_RIGHT_STRAIGHT_UP,
    TOP_LEFT_STRAIGHT_DOWN,
    TOP_RIGHT_STRAIGHT_DOWN,

    BOTTOM_LEFT_HALF_IN_UP;

    /**
     * get a strategy based on the index
     * @param s index of the strategy
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @return the strategy
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid for the strategy
     */
    public static RoomStrategy getStrategy(int s, int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        Strategies strategy = Strategies.values()[s];
        switch (strategy) {
            case BOTTOM_LEFT_UP:
                return new BottomLeftUp(roomsPerRow, roomsPerColumn);
            case BOTTOM_RIGHT_UP:
                return new BottomRightUp(roomsPerRow, roomsPerColumn);
            case TOP_LEFT_DOWN:
                return new TopLeftDown(roomsPerRow, roomsPerColumn);
            case TOP_RIGHT_DOWN:
                return new TopRightDown(roomsPerRow, roomsPerColumn);
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
