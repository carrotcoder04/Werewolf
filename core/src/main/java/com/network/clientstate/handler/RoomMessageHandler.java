package com.network.clientstate.handler;

import com.io.Reader;
import com.message.tag.MessageTag;
import com.network.Client;
import com.werewolf.*;
import com.werewolf.game.Player;
import com.werewolf.game.PlayerBoard;
import com.werewolf.game.PlayerInfo;
import com.werewolf.game.role.Role;
import com.werewolf.game.role.RoleInfo;
import com.werewolf.screen.RoomScreen;

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
                    RolePanel.getInstance().setRoles(roleInfos);
                });
                break;
            case MessageTag.MY_ROLE:
                RoleInfo roleInfo = RoleInfo.values()[reader.nextByte()];
                InvokeOnMainThread.invoke(() -> {
                    Role role = Role.createRole(roleInfo);
                    Player.getMainPlayer().setRole(role);
                    BoxChat.getInstance().addNotification("Vai trò của bạn là: " + roleInfo.getName() + ".");
                    RolePanel.getInstance().setMainRole(roleInfo);
                });
                break;
            case MessageTag.CHAT:
                InvokeOnMainThread.invoke(() -> {
                    Player player = PlayerBoard.getInstance().getSlot(reader.nextByte()).getPlayer();
                    BoxChat.getInstance().addText(player, reader.nextString());
                });
                break;
            case MessageTag.NOTIFICATION:
                InvokeOnMainThread.invoke(() -> {
                    BoxChat.getInstance().addNotification(reader.nextString());
                });
                break;
            case MessageTag.CHANGE_GAME_STATE:
                GameState gameState = GameState.values()[reader.nextByte()];
                InvokeOnMainThread.invoke(() -> {
                    GameTime.getInstance().setState(gameState);
                });
                break;
            case MessageTag.ROLE_DISCLOSURE: {
                byte disclosureId = reader.nextByte();
                if (disclosureId == Player.getMainPlayer().getId()) {
                    return;
                }
                RoleInfo disclosureRoleInfo = RoleInfo.values()[reader.nextByte()];
                InvokeOnMainThread.invoke(() -> {
                    Role role = Role.createRole(disclosureRoleInfo);
                    Player player = PlayerBoard.getInstance().getSlot(disclosureId).getPlayer();
                    player.setRole(role);
                });
                break;
            }
            case MessageTag.COUNT_DOWN:
                String message = reader.nextString();
                InvokeOnMainThread.invoke(() -> {
                    RoomScreen.getInstance().setInfoText(message);
                });
                break;
            case MessageTag.VOTE:
            {
                Player voter = PlayerBoard.getInstance().getSlot(reader.nextByte()).getPlayer();
                Player target = PlayerBoard.getInstance().getSlot(reader.nextByte()).getPlayer();
                int numVotes = reader.nextByte();
                InvokeOnMainThread.invoke(() -> {
                    voter.vote(target);
                    target.getMySlot().setNumVote(numVotes);
                });
                break;
            }
            case MessageTag.UN_VOTE:
            {
                Player voter = PlayerBoard.getInstance().getSlot(reader.nextByte()).getPlayer();
                Player target = PlayerBoard.getInstance().getSlot(reader.nextByte()).getPlayer();
                int numVotes = reader.nextByte();
                InvokeOnMainThread.invoke(() -> {
                    voter.unVote();
                    target.getMySlot().setNumVote(numVotes);
                });
                break;
            }
            case MessageTag.SET_CAN_VOTE:
            {
                Player.getMainPlayer().setCanVote(reader.nextByte() == 1);
                break;
            }
            case MessageTag.SET_CAN_CHAT:
            {
                Player.getMainPlayer().setCanChat(reader.nextByte() == 1);
                break;
            }
            case MessageTag.SET_ALIVE:
            {
                Player player = PlayerBoard.getInstance().getSlot(reader.nextByte()).getPlayer();
                player.setAlive(reader.nextByte() == 1);
            }
        }
    }
    @Override
    public void onExit(Client client) {

    }
}
