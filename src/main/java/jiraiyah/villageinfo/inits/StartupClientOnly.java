package jiraiyah.villageinfo.inits;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
    @SubscribeEvent
    public static void registerClientSetup(FMLClientSetupEvent event) {
        KeyBindings.register();
    }
}
