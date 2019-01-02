package com.maelstrom.arcanemechina.common.block.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import net.minecraft.block.properties.PropertyHelper;

public class PropertyString extends PropertyHelper<String>{
	private final ImmutableSet<String> allowedValues;
    private final Map<Integer, String> nameToValue = Maps.<Integer, String>newHashMap();
    
	protected PropertyString(String name,	Collection<String> allowedValues) {
		super(name, String.class);
		this.allowedValues = ImmutableSet.copyOf(allowedValues);
		int index = 0;
		for(String s : this.allowedValues)
		{
			if(nameToValue.containsValue(s))
                throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
			nameToValue.put(index, s);
		}
	}

	@Override
	public Collection<String> getAllowedValues() {
		return this.allowedValues;
	}

	@Override
	public Optional<String> parseValue(String value){
		return Optional.<String>fromNullable(nameToValue.get(Index(value)));
	}

	@Override
	public String getName(String value) {
		return parseValue(value).get();
	}

	public static PropertyString Create(String name, Collection<String> values)
	{
		return new PropertyString(name,values);
	}

	public static PropertyString Create(String name, String[] values)
	{
		ArrayList<String> list = new ArrayList<String>();
		for(String value : values)
			list.add(value.toLowerCase());
		return new PropertyString(name,list);
	}
	public int Index(String value) {
		if(nameToValue.containsValue(value.toLowerCase()))
			for(int i : nameToValue.keySet())
			{
				if(nameToValue.get(i) == value.toLowerCase())
					return i;
			}
		return 0;
	}
	public String getName(int value)
	{
		return nameToValue.get(value);
	}
	
}
