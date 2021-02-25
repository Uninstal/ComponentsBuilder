# ComponentsBuilder
Simplified creation of clickable super-messages.

### Description
This code allows you to easily create text in the **BaseComponent[]**
format to send clickable messages to the player. The code has a built-in
option to replace Placeholder's (%placeholder%). The code takes into account 
the links to the sites and that is why it is unique.

### How create

To create a clickable message, use:
```java
SuperComponentBuilder builder = new SuperComponentBuilder(text, new ComponentHolders());
```
or:
```java
SuperComponentBuilder builder = new SuperComponentBuilder(text, null);
```
However, the second method is better to use if you will definitely add some replacement.

### Replacement

To make this very replacement, use:
```java
SuperComponentBuilder builder = new SuperComponentBuilder(text, new ComponentHolders()
	.component("%player%", player.getName(), null, null));
```
or:
```java
SuperComponentBuilder builder = new SuperComponentBuilder(text, null);
ComponentHolders componentHolders = new ComponentHolders();
componentHolders.component("%player%", player.getName(), null, null);
builder.setHolders(componentHolders);
```
You can make an unlimited number of such substitutions:
```java
SuperComponentBuilder builder = new SuperComponentBuilder(text, new ComponentHolders()
	.component("%player%", player.getName(), null, null)
	.component("%ip%", player.getAddress().getHostName(), null, null)
	.component("%health%", String.valueOf(player.getHealth()), null, null));
```
### Events

You also, as mentioned earlier, can make posts globalnymi and outputting anything when you hover:

```java
net.md_5.bungee.api.chat.ClickEvent.Action ACTION = net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND;
net.md_5.bungee.api.chat.HoverEvent.Action ACTION2 = net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT;
		
componentHolders.component("%player%", player.getName(), 
	new ClickEvent(ACTION, "tell "),
	new HoverEvent(ACTION2, TextComponent.fromLegacyText("Send message")));
		
componentHolders.component("%ip%", player.getAddress().getHostName(), 
	new ClickEvent(ACTION, "tell "),
	new HoverEvent(ACTION2, TextComponent.fromLegacyText("Send message")));
```
### Build

And finally, to finish the whole thing, you need to use:
```java
builder.build().getBuildedComponent();
```

We get something like this code:
```java
public class Test {
	
 public Test() {
		
	for(Player player : Bukkit.getOnlinePlayers()) {
			
		sendClickableMessage(player, "User: %player%");
		sendClickableMessage(player, "IP: %ip%");
		sendClickableMessage(player, "Health: %health%");
			
		continue;
	}
 }

 public void sendClickableMessage(Player player, String text) {
		
	net.md_5.bungee.api.chat.ClickEvent.Action ACTION = net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND;
	net.md_5.bungee.api.chat.HoverEvent.Action ACTION2 = net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT;
		
	SuperComponentBuilder builder = new SuperComponentBuilder(text, null);
	ComponentHolders componentHolders = new ComponentHolders();
		
	componentHolders.component("%player%", player.getName(), 
		new ClickEvent(ACTION, "tell "),
		new HoverEvent(ACTION2, TextComponent.fromLegacyText("Send message")));
		
	componentHolders.component("%ip%", player.getAddress().getHostName(), 
		new ClickEvent(ACTION, "tell "),
		new HoverEvent(ACTION2, TextComponent.fromLegacyText("Send message")));
		
	BaseComponent[] component = builder.build().getBuildedComponent();
	player.spigot().sendMessage(component);
 }
}
```
