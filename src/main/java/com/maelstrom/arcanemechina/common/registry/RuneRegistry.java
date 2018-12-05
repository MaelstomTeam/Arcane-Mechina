package com.maelstrom.arcanemechina.common.registry;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.api.RuneActivity;
import com.maelstrom.arcanemechina.common.arrays.BarrierActivity;
import com.maelstrom.arcanemechina.common.arrays.BasicActivity;
import com.maelstrom.arcanemechina.common.block.BlockList;
import com.maelstrom.arcanemechina.common.event.EventRuneActivation;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
public class RuneRegistry
{

	static Logger LOGGER = LogManager.getLogger("arcanemechina] [Array");
	public static void Initialize()
	{
		try {
			LOGGER.info("Loading array's patterns from file");
			LoadArray("assets/arcanemechina/array/basic.xml").setActivity(new BasicActivity());
			LoadArray("assets/arcanemechina/array/barrier.xml").setActivity(new BarrierActivity());
			LoadArray("assets/arcanemechina/array/dimensional_gate.xml").setActivity(new BasicActivity());
			LoadArray("assets/arcanemechina/array/shrink.xml").setActivity(new BasicActivity());
		} catch (Exception e) {
			LOGGER.info("Something Went Wrong!");
			e.printStackTrace();
		}
	}
	
	private static Map<String,Array> arrays = new HashMap<String,Array>();
	
	public static void RegisterArray(Array a) throws Exception
	{
		if(a.ID == null)
			throw new Exception("Array has no id!! Source: " + a.source);
		else
			arrays.put(a.ID, a);
	}
	
	public static Array checkRune(World w, BlockPos pos)
	{
		int hits = 0;
		String hitName = "none";
		for(Array array : arrays.values())
		{
			if(array.isValid(w, pos))
			{
				hits++;
				hitName = array.ID;
			}
		}
		return hits == 1 ? arrays.get(hitName) : null;
	}
	public static Array getRune(String id)
	{
		return arrays.get(id);
	}
	
	public static Array LoadArray(String location) throws Exception
	{
		ClassLoader CLDR = Array.class.getClassLoader();
		InputStream inStrm = CLDR.getResourceAsStream(location);
		JAXBContext jaxbContext = JAXBContext.newInstance(Array.class);
		Unmarshaller jaxbu = jaxbContext.createUnmarshaller();
		Array array = (Array) jaxbu.unmarshal(inStrm);
		array.source = location;
		RegisterArray(array);
		return array;
	}
	public static void createArray(Array array)
	{
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Array.class);
			Marshaller jaxbu = jaxbContext.createMarshaller();
			jaxbu.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbu.marshal(array, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

@XmlRootElement(name = "Array")
	public static class Array
	{
		public String source;
		public String ID;
		public List<BlockData> data;
		
		public Array() {}
		public Array(String id)
		{
			ID =id;
		}
		private RuneActivity activity;
		public RuneActivity getActivity()
		{
			return activity;
		}
		public void setActivity(RuneActivity activity)
		{
			this.activity = activity;
		}
		public boolean isValid(World w, BlockPos pos)
		{
			ArcaneMechina.LOGGER.info(this.ID);
			for(BlockData blockData : data)
			{
				ArcaneMechina.LOGGER.info(w.getBlockState(pos.add(blockData.X, 0, blockData.Z)).equals(blockData.getState()));
				if(!w.getBlockState(pos.add(blockData.X, 0, blockData.Z)).equals(blockData.getState()))
						return false;
			}
			return activity != null;
			
		}
		public boolean isValidClass()
		{
			return !ID.isEmpty() && data != null && activity != null;
		}
		public void run(World w, BlockPos pos, EntityPlayer player)
		{
			MinecraftForge.EVENT_BUS.post(new EventRuneActivation(pos, player, this));
			/*
			if(activity.isValid(w, pos))
			{
				activity.runActivity(w, pos);
			}
			else
			{
				MinecraftForge.EVENT_BUS.post(activity.getBackfireResult(w, pos, player));
			}*/
		}
	}
	public static class BlockData
	{
		public Integer META;
		public int X;
		public int Z;
		public IBlockState getState() {
			if(META == null)
				return BlockList.Rune.getDefaultState();
			return BlockList.Rune.getStateFromMeta(META);
		}
	}

}
