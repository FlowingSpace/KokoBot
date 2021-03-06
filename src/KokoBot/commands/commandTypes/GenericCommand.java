package KokoBot.commands.commandTypes;

import java.io.IOException;
import java.util.ArrayList;

import KokoBot.commands.Command;
import KokoBot.commands.CommandManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GenericCommand implements Command{

	
	public GenericEmoteShellEvent[] emotelisteners;
	
	public String Name;
	GenericEventFunctional Event;
	public String desc;
	public GenericCommand(String Name, GenericEventFunctional Event, String desc, GenericEmoteShellEvent[] emotelisteners) {
		this.Name = Name;
		this.Event = Event;
		this.desc = desc;
		this.emotelisteners = emotelisteners;
		if(emotelisteners==null) {
			this.emotelisteners = new GenericEmoteShellEvent[0];
		}
	}
	
	public GenericCommand(String Name, GenericEventFunctional Event, String desc) {
		this(Name,Event,desc,null);
	}



	@Override
	public boolean ListenForEvent(String Input) {
		return Input.startsWith(Name);
	}

	@Override
	public void onEvent(MessageReceivedEvent Message) throws IOException {
		Message msg = Event.onEvent(Message);
		if(CommandManager.emoteevents.get(Message.getGuild().getId())==null) {
			CommandManager.emoteevents.put(Message.getGuild().getId(), new ArrayList<GenericEmoteEvent>());
		}
		for(GenericEmoteShellEvent shell:emotelisteners) {
			CommandManager.emoteevents.get(Message.getGuild().getId()).add(new GenericEmoteEvent(shell.emote, shell.Event,msg.getId(), shell.collectiveID));
		}
	}

	@Override
	public String getName() {
		return Name;
	}

	@Override
	public String getDescription() {
		return desc;
	}


}
