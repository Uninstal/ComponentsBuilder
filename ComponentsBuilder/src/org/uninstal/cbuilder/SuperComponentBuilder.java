package org.uninstal.cbuilder;

import java.net.URL;

import org.bukkit.ChatColor;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class SuperComponentBuilder {

	private BaseComponent[] baseComponent = new BaseComponent[1];
	
	private String text;
	private ComponentHolders holders;

	public SuperComponentBuilder(String text, ComponentHolders holders) {
		this.text = text;
		this.holders = holders;
	}
	
	public SuperComponentBuilder setHolders(ComponentHolders holders) {
		this.holders = holders;
		return this;
	}
	
	public SuperComponentBuilder build() {
		
		BaseComponent baseComponent = new TextComponent();
		String[] urls = text.split(" ");
		
		boolean first = true;
		for(String cut : urls) {
			if(!first) baseComponent.addExtra(" ");
			
			boolean replace = false;
			for(String textId : holders.getTextIds()) {
				
				if(cut.equalsIgnoreCase(textId)) {
					baseComponent.addExtra(holders.getComponents().get(textId));
					
					replace = true;
					continue;
				}
				
				if(cut.contains(textId)) {
					
					String[] texts = cut.split(textId);
					int k = 1;
					
					for(String text : texts) {
						
						baseComponent.addExtra(text.replace("&", "§"));
						if(k != texts.length || texts.length == 1) 
							baseComponent.addExtra(holders.getComponents().get(textId));
						
						replace = true;
						k++;
					}
					
					continue;
				}
				else continue;
			}
			
			if(!replace) {
				
				if(isURL(cut)) {
					
					BaseComponent b = new TextComponent(cut);
					b.setClickEvent(new ClickEvent(Action.OPEN_URL, "https:/" + cut));
					
					baseComponent.addExtra(b);
				}
				
				else baseComponent.addExtra(cut.replace("&", "§"));
			}
			
			first = false;
		}
		
		this.baseComponent[0] = baseComponent;
		return this;
	}
	
	public BaseComponent[] getBuildedComponent() {
		return baseComponent;
	}
	
	private boolean isURL(String text) {
		text = ChatColor.stripColor(text);
		
		if(text.isEmpty()
				|| text.length() < 5
				|| !text.contains(".")
				|| !text.replace(".", "").matches("^[A-Za-z]+$")){
			return false;
		}
		
		try {
			
			new URL("https:/" + text).toURI();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
}
