/**
 * Copyright 2016 VillageInfoMod (Jiraiyah)
 *
 * project link : http://minecraft.curseforge.com/projects/village-info
 *
 * Licensed under The MIT License (MIT);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jiraiyah.villageinfo;

import jiraiyah.villageinfo.events.ChunkDataHandler;
import jiraiyah.villageinfo.events.KeyBindingHandler;
import jiraiyah.villageinfo.events.VillageDataHandler;
import jiraiyah.villageinfo.events.WorldDataCollector;
import jiraiyah.villageinfo.events.WorldSpawnHandler;
import jiraiyah.villageinfo.inits.StartupClientOnly;
import jiraiyah.villageinfo.inits.StartupCommon;
import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(Reference.MOD_ID)
public class VillageInfo {
    public static final String MODID = "villageinfo";
    public static IEventBus MOD_EVENT_BUS;

    public static boolean solidDraw;
    public static boolean villageBorder;
    public static boolean showVillages;
    public static boolean villageDoors = true;
    public static boolean villageSphere;
    public static boolean villageGolem = true;
    public static boolean villageInfoText;
    public static boolean villageCenter = true;
    public static boolean disableDepth = true;
    public static boolean perVillageColor;
    public static boolean chunkBorder;

    public VillageInfo() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        // The event bus register method will search these classes for methods which are interested in startup events
        //    (i.e. methods that are decorated with the @SubscribeEvent annotation)

        // Beware - there are two event busses: the MinecraftForge.EVENT_BUS and your own ModEventBus.
        //  If you subscribe your event to the wrong bus, it will never get called.
        // likewise, beware of the difference between static and non-static methods, i.e.
        //  If you register a class, but the @SubscribeEvent is on a non-static method, it won't be called.  e.g.
        //  MOD_EVENT_BUS.register(MyClass.class);
        //  public class ServerLifecycleEvents {
        //    @SubscribeEvent
        //      public void onServerStartingEvent(FMLServerStartingEvent event) { // missing static! --> never gets called}
        //  }

        // Based on my testing: ModEventBus is used for setup events only, in the following order:
        // * RegistryEvent of all types
        // * ColorHandlerEvent for blocks & items
        // * ParticleFactoryRegisterEvent
        // * FMLCommonSetupEvent
        // * TextureStitchEvent
        // * FMLClientSetupEvent or FMLDedicatedServerSetupEvent
        // * ModelRegistryEvent
        // * Other ModLifecycleEvents such as InterModEnqueueEvent, InterModProcessEvent
        // Everything else: the MinecraftForge.EVENT_BUS

        MOD_EVENT_BUS.register(StartupClientOnly.class);
        MOD_EVENT_BUS.register(StartupCommon.class);

        MinecraftForge.EVENT_BUS.register(new WorldDataCollector());
        MinecraftForge.EVENT_BUS.register(new VillageDataHandler());
        MinecraftForge.EVENT_BUS.register(new WorldSpawnHandler());
        MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
        MinecraftForge.EVENT_BUS.register(new ChunkDataHandler());
        // NetworkMessages.register();

        //----------------
        //MOD_EVENT_BUS.register(minecraftbyexample.usefultools.debugging.StartupClientOnly.class);
        //MOD_EVENT_BUS.register(minecraftbyexample.usefultools.debugging.StartupCommon.class);
    }
}
