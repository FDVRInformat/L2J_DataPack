/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.group_template;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * Evas Gift Boxes AI.
 * @author Gigiikun
 */
public class EvasGiftBoxes extends Quest
{
	private static final int GIFTBOX = 32342;
	
	private static final int KISSOFEVA = 1073;
	
	// index 0: without kiss of eva
	// index 1: with kiss of eva
	// chance,itemId,...
	private static final int[][] CHANCES =
	{
		{
			2,
			9692,
			1,
			9693
		},
		{
			100,
			9692,
			50,
			9693
		}
	};
	
	public EvasGiftBoxes(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addKillId(GIFTBOX);
		addSpawnId(GIFTBOX);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setIsNoRndWalk(true);
		return super.onSpawn(npc);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == GIFTBOX)
		{
			int isKissOfEvaBuffed = 0;
			if (killer.getFirstEffect(KISSOFEVA) != null)
			{
				isKissOfEvaBuffed = 1;
			}
			for (int i = 0; i < CHANCES[isKissOfEvaBuffed].length; i += 2)
			{
				if (getRandom(100) < CHANCES[isKissOfEvaBuffed][i])
				{
					giveItems(killer, CHANCES[isKissOfEvaBuffed][i + 1], 1);
				}
			}
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new EvasGiftBoxes(-1, EvasGiftBoxes.class.getSimpleName(), "ai");
	}
}