package com.github.skystardust.keyboardmaster;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import org.lwjgl.input.Keyboard;

import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.HashSet;

/**
 * @author Himmelt
 */
public class MyTickHandler implements ITickHandler {

    private static final HashSet<Integer> KEY_STATES = new HashSet<>();

    @Override
    public void tickStart(EnumSet<TickType> types, Object... tickData) {
        for (TickType type : types) {
            if (type == TickType.CLIENT) {
                int code = Keyboard.getEventKey();
                if (Keyboard.getEventKeyState()) {
                    if (!KEY_STATES.contains(code)) {
                        System.out.println("MyTickHandler:" + code);
                        sendKeyAction(code);
                        KEY_STATES.add(code);
                    }
                } else {
                    KEY_STATES.remove(code);
                }
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> types, Object... tickData) {
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return "MyTickHandler";
    }

    private void sendKeyAction(int code) {
        NetClientHandler clientHandler = Minecraft.getMinecraft().getNetHandler();
        if (clientHandler != null) {
            System.out.println("sendQueue:" + code);
            clientHandler.addToSendQueue(new Packet250CustomPayload("KM|KEYBOARD", String.valueOf(code).getBytes(StandardCharsets.UTF_8)));
        }
    }
}
