package com.systemsmart.factory;

import com.systemsmart.entity.Room;

public class RoomFactory {
    public static Room createRoom(String type, int roomNumber, double price, boolean status) {
        return new Room.Builder().setRoomNumber(roomNumber)
                .setType(type).setPrice((int) price)
                .setStatus(status).build();
    }


}

