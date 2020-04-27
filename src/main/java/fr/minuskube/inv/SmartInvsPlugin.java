package fr.minuskube.inv;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartInvsPlugin extends JavaPlugin {

    private static SmartInvsPlugin instance;
    private static InventoryManager invManager;

    private TestGUI testGUI;

    @Override
    public void onEnable() {
        instance = this;

        invManager = new InventoryManager(this);
        invManager.init();

        testGUI = new TestGUI();
    }

    public static InventoryManager manager() {
        return invManager;
    }

    public static SmartInvsPlugin instance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("testgui")) {
            this.testGUI.getTestInv().open((Player) sender);
        }
        return false;
    }
}
