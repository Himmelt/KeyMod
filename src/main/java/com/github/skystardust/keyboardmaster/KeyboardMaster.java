package com.github.skystardust.keyboardmaster;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

import java.nio.charset.StandardCharsets;

/**
 * @author Himmelt
 */
@Mod(
        modid = "keyboardmaster",
        version = "1.0",
        useMetadata = true
)
public class KeyboardMaster {

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        TickRegistry.registerTickHandler(new MyTickHandler(), Side.CLIENT);
    }

    @ForgeSubscribe
    public void onMouseAction(MouseEvent mouseInputEvent) {
        sendMouseAction(mouseInputEvent.button);
    }

    private void sendMouseAction(int code) {
        NetClientHandler clientHandler = Minecraft.getMinecraft().getNetHandler();
        if (code != -1 && clientHandler != null) {
            clientHandler.addToSendQueue(new Packet250CustomPayload("KM|MOUSE", String.valueOf(code).getBytes(StandardCharsets.UTF_8)));
        }
    }
}
