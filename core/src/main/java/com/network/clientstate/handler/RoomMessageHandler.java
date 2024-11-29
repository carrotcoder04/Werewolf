package com.network.clientstate.handler;

import com.io.Reader;
import com.message.tag.MessageTag;
import com.network.Client;
import com.werewolf.InvokeOnMainThread;
import com.werewolf.game.Player;
import com.werewolf.game.PlayerBoard;
import com.werewolf.game.PlayerInfo;
import com.werewolf.game.role.Role;
import com.werewolf.game.role.RoleInfo;

import java.util.ArrayList;

public class RoomMessageHandler extends ClientMessageHandler {
    @Override
    public void onEnter(Client client) {
        client.send(MessageTag.JOIN_ROOM);
    }
    @Override
    public void onMessage(Client client, byte tag, Reader reader) {
        switch (tag) {
            case MessageTag.UPDATE_ROOM:
                InvokeOnMainThread.invoke(() -> {
                    PlayerInfo playerInfo = new PlayerInfo(reader);
                    PlayerBoard.getInstance().updateRoom(playerInfo);
                });
                break;
            case MessageTag.UPDATE_SLOT_EMPTY:
                int slotId = reader.nextInt();
                InvokeOnMainThread.invoke(() -> {
                    PlayerBoard.getInstance().updateSlotEmpty(slotId);
                });
                break;
            case MessageTag.ALL_ROLES:
                ArrayList<RoleInfo> roleInfos = new ArrayList<>();
                RoleInfo[] roleInfoArr = RoleInfo.values();
                try {
                    while (true) {
                        RoleInfo roleInfo = roleInfoArr[reader.nextByte()];
                        roleInfos.add(roleInfo);
                    }
                }
                catch (IndexOutOfBoundsException e) {
                }
                InvokeOnMainThread.invoke(() -> {
                   PlayerBoard.getInstance().setAllRoleInfos(roleInfos);
                });
                break;
            case MessageTag.MY_ROLES:
                RoleInfo roleInfo = RoleInfo.values()[reader.nextByte()];
                Role role = Role.createRole(roleInfo);
                InvokeOnMainThread.invoke(() -> {
                    Player.getMainPlayer().setRole(role);
                });
        }
    }
    @Override
    public void onExit(Client client) {

    }
}
