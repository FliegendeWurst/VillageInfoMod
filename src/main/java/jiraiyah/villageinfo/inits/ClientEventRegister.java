/**
 * Copyright 2016 Village Info (Jiraiyah)
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

import jiraiyah.villageinfo.events.VillageDataHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientEventRegister
{
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new VillageDataHandler());
		//Log.info("=========================================================> Registered Client Events");
	}
}