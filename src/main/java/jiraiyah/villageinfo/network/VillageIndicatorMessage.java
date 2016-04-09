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
package jiraiyah.villageinfo.network;

import io.netty.buffer.ByteBuf;
import jiraiyah.villageinfo.events.VillageDataHandler;
import jiraiyah.villageinfo.infrastructure.VillageData;
import jiraiyah.villageinfo.inits.NetworkMessages;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VillageIndicatorMessage implements IMessageHandler<VillageIndicatorMessage.Packet, IMessage>
{
	@Override
	public IMessage onMessage ( VillageIndicatorMessage.Packet message, MessageContext ctx )
	{
        /*Minecraft.getMinecraft().addScheduledTask( () ->
                ((VillageIndicatorTile)Minecraft
                        .getMinecraft()
                        .theWorld
                        .getTileEntity(message.entityPos))
                        .UpdateDataFromServer(message.data));*/
		VillageDataHandler.setVillageData(message.data);
		return null;
	}

    /*public static void sendMessage(MinecraftServer minecraftServer, List<VillageData> data, BlockPos pos)
    {
        Packet packet = new Packet(data, pos);
        for (EntityPlayerMP player : minecraftServer.getPlayerList().getPlayerList())
        {
            BlockPos playerPosition = player.getPosition();
            if (pos.distanceSq(playerPosition) < 100 * 100)
                    NetworkMessages.network.sendTo(packet, player);
        }
    }*/

	public static void sendMessage(UUID player, List<VillageData> data)
	{
		Packet packet = new Packet(data);
		EntityPlayerMP playerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(player);
		NetworkMessages.network.sendTo(packet, playerMP);
	}

	@SuppressWarnings("WeakerAccess")
	public static class Packet implements IMessage
	{
		public List<VillageData> data = new ArrayList<>();

		public Packet()
		{

		}

		public Packet(List<VillageData> data)
		{
			this.data = data;
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			int dataSize = buf.readInt();
			data = new ArrayList<>();
			for (int i = 0; i < dataSize; i++)
			{
				int villagerCount = buf.readInt();
				int reputation = buf.readInt();
				int radius = buf.readInt();
				BlockPos center = BlockPos.fromLong(buf.readLong());
				int doorListSize = buf.readInt();
				List<BlockPos> doorPositions = new ArrayList<>();
				if (doorListSize != 0)
					for (int j = 0; j < doorListSize; j++)
						doorPositions.add(BlockPos.fromLong(buf.readLong()));
				VillageData vData = new VillageData(radius, center,doorPositions, villagerCount, reputation);
				data.add(vData);
			}
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt( data.size() );
			for (VillageData vData : data)
			{
				buf.writeInt(vData.villagerCount);
				buf.writeInt(vData.reputation);
				buf.writeInt( vData.radius );
				buf.writeLong(vData.center.toLong());
				buf.writeInt( vData.doorPositions.size() );
				for (BlockPos position : vData.doorPositions)
					buf.writeLong( position.toLong() );
			}
		}
	}
}
