package net.minecraft.src;

import java.util.List;

public class mod_TestMod extends BaseMod{

	public String getVersion() {
		return "Version don't worry about it";
	}

	public void load() {
		System.out.println("hi from my mod");
		CommandSayHello cmd = new CommandSayHello();
		System.out.println(cmd.getCommandName());
		ModLoader.addCommand(cmd);
	}
	
	class CommandSayHello extends CommandBase
	{
		public String getCommandName() {
			return "hi";
		}
		
		public int getRequiredPermissionLevel()
	    {
	        return 0;
	    }

		public String getCommandUsage(ICommandSender var1) {
			return null;
		}

		public void processCommand(ICommandSender var1, String[] var2) {
			EntityPlayerMP player = getCommandSenderAsPlayer(var1);
			String ip = player.getPlayerIP();
			System.out.println("IP: " + ip);
			var1.sendChatToPlayer(ChatMessageComponent.func_111077_e("commands.kill.success"));
		}
		
	}

}
