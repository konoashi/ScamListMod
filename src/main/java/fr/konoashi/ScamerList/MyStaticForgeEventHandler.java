package fr.konoashi.ScamerList;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class MyStaticForgeEventHandler {

    @SubscribeEvent
    public static void pickupItem(EntityItemPickupEvent event) {
        System.out.println("Item picked up!");
    }
}
