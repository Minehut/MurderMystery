/*
 * MurderMystery - Find the murderer, kill him and survive!
 * Copyright (C) 2019  Plajer's Lair - maintained by Tigerpanzer_02, Plajer and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.plajer.murdermystery.handlers.setup;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajer.murdermystery.Main;
import pl.plajer.murdermystery.arena.Arena;
import pl.plajer.murdermystery.handlers.ChatManager;
import pl.plajer.murdermystery.handlers.setup.components.ArenaRegisterComponent;
import pl.plajer.murdermystery.handlers.setup.components.MiscComponents;
import pl.plajer.murdermystery.handlers.setup.components.PlayerAmountComponents;
import pl.plajer.murdermystery.handlers.setup.components.SpawnComponents;
import pl.plajer.murdermystery.handlers.setup.components.SpecialBlocksComponents;
import pl.plajerlair.commonsbox.minecraft.configuration.ConfigUtils;

/**
 * @author Plajer
 * <p>
 * Created at 25.05.2019
 */
public class SetupInventory {

  public static final String VIDEO_LINK = "https://tutorial.plajer.xyz";
  private static Random random = new Random();
  private static Main plugin = JavaPlugin.getPlugin(Main.class);
  private FileConfiguration config = ConfigUtils.getConfig(plugin, "arenas");
  private Arena arena;
  private Player player;
  private Gui gui;
  private SetupUtilities setupUtilities;

  public SetupInventory(Arena arena, Player player) {
    this.arena = arena;
    this.player = player;
    this.setupUtilities = new SetupUtilities(config, arena);
    prepareGui();
  }

  private void prepareGui() {
    this.gui = new Gui(plugin, 4, "Murder Mystery Arena Setup");
    this.gui.setOnGlobalClick(e -> e.setCancelled(true));
    StaticPane pane = new StaticPane(9, 4);
    this.gui.addPane(pane);

    prepareComponents(pane);
  }

  private void prepareComponents(StaticPane pane) {
    SpawnComponents spawnComponents = new SpawnComponents();
    spawnComponents.prepare(this);
    spawnComponents.injectComponents(pane);

    PlayerAmountComponents playerAmountComponents = new PlayerAmountComponents();
    playerAmountComponents.prepare(this);
    playerAmountComponents.injectComponents(pane);

    MiscComponents miscComponents = new MiscComponents();
    miscComponents.prepare(this);
    miscComponents.injectComponents(pane);

    ArenaRegisterComponent arenaRegisterComponent = new ArenaRegisterComponent();
    arenaRegisterComponent.prepare(this);
    arenaRegisterComponent.injectComponents(pane);

    SpecialBlocksComponents specialBlocksComponents = new SpecialBlocksComponents();
    specialBlocksComponents.prepare(this);
    specialBlocksComponents.injectComponents(pane);
  }

  private void sendProTip(Player p) {
    int rand = random.nextInt(16 + 1);
    switch (rand) {
      case 0:
        p.sendMessage(ChatManager.colorRawMessage("&e&lTIP: &7Help us translating plugin to your language here: https://translate.plajer.xyz"));
        break;
      case 1:
        p.sendMessage(ChatManager.colorRawMessage("&e&lTIP: &7LeaderHeads leaderboard plugin is supported with our plugin! Check here: https://bit.ly/2IH5zkR"));
        break;
      case 2:
        p.sendMessage(ChatManager.colorRawMessage("&e&lTIP: &7We are open source! You can always help us by contributing! Check https://github.com/Plajer-Lair/MurderMystery"));
        break;
      case 3:
        p.sendMessage(ChatManager.colorRawMessage("&e&lTIP: &7Need help? Check wiki &8https://wiki.plajer.xyz/minecraft/murdermystery &7or discord https://discord.gg/UXzUdTP"));
        break;
      case 4:
        p.sendMessage(ChatManager.colorRawMessage("&e&lTIP: &7Suggest new ideas for the plugin or vote on current ones! https://uservoice.plajer.xyz/index.php?id=MurderMystery"));
        break;
      default:
        break;
    }
  }

  public void openInventory() {
    sendProTip(player);
    gui.show(player);
  }

  public Main getPlugin() {
    return plugin;
  }

  public FileConfiguration getConfig() {
    return config;
  }

  public Arena getArena() {
    return arena;
  }

  public Player getPlayer() {
    return player;
  }

  public Gui getGui() {
    return gui;
  }

  public SetupUtilities getSetupUtilities() {
    return setupUtilities;
  }

}
