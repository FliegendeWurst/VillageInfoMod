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
package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.network.SpawnPlayerMessage;
import jiraiyah.villageinfo.network.SpawnServerMessage;
import jiraiyah.villageinfo.network.VillagePlayerMessage;
import jiraiyah.villageinfo.network.VillageServerMessage;
import jiraiyah.villageinfo.references.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.relauncher.Side;

import static jiraiyah.villageinfo.VillageInfo.MODID;

public class NetworkMessages
{
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void register() {
		int id = 0;
		INSTANCE.registerMessage(id++, VillageServerMessage.class);
		INSTANCE.registerMessage(id++, VillagePlayerMessage.class);
		INSTANCE.registerMessage(id++, SpawnServerMessage.class);
		INSTANCE.registerMessage(id++, SpawnPlayerMessage.class);
		//Log.info("=========================================================> Registered Network Messages");
	}
}
