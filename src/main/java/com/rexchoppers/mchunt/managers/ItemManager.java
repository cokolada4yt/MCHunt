package com.rexchoppers.mchunt.managers;

import com.rexchoppers.mchunt.MCHunt;
import com.rexchoppers.mchunt.items.ItemBuilder;
import com.rexchoppers.mchunt.permissions.Permissions;
import com.rexchoppers.mchunt.util.Format;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {
    private final MCHunt plugin;

    public ItemManager(MCHunt plugin) {
        this.plugin = plugin;
    }

    public Map<Integer, ItemBuilder> getSeekerHotbarItems() {
        return new HashMap<>() {{
            put(0, itemSeekerWeapon());
        }};
    }

    public Map<Integer, ItemBuilder> getDefaultHotbarArenaSetupItems() {
        return new HashMap<>() {{
            put(1, itemArenaSetupToolSelection());
            put(2, itemArenaSetupConfig());
            put(3, itemArenaSetupActions());
        }};
    }

    public List<ItemBuilder> getHotbarArenaSetupItems() {
        List<ItemBuilder> items = new ArrayList<>();
        items.add(itemArenaSetupSelection());
        items.add(itemArenaSetupToolSelection());
        items.add(itemArenaSetupConfig());
        items.add(itemArenaSetupActions());
        return items;
    }

    public void setArenaSetupItems(Player player) {
        for (Map.Entry<Integer, ItemBuilder> entry : getDefaultHotbarArenaSetupItems().entrySet()) {
            player.getInventory().setItem(entry.getKey(), entry.getValue().build());
        }

        // TODO - Create method to do actions
    }

    public void setSeekerItems(Player player) {
        for (Map.Entry<Integer, ItemBuilder> entry : getSeekerHotbarItems().entrySet()) {
            player.getInventory().setItem(entry.getKey(), entry.getValue().build());
        }
    }

    public String getItemAction(ItemStack itemStack) {
        NamespacedKey key = new NamespacedKey(this.plugin, "action");
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {
            return null;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(key , PersistentDataType.STRING)) {
            String s = container.get(key, PersistentDataType.STRING);
            return s;
        }

        return null;
    }

    public boolean getItemDroppable(ItemStack itemStack) {
        NamespacedKey key = new NamespacedKey(this.plugin, "mchunt_droppable");
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta == null) {
            return true;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(key, PersistentDataType.BOOLEAN)) {
            return container.get(key, PersistentDataType.BOOLEAN);
        }

        return true;
    }

    public List<Material> getBlockMaterials() {
        String[] excludedBlocks = {
                Material.AIR.name(),
                Material.BARRIER.name(),
                Material.CAVE_AIR.name(),
                Material.VOID_AIR.name(),
                Material.BUBBLE_COLUMN.name(),
                Material.STRUCTURE_VOID.name(),
                Material.CHAIN_COMMAND_BLOCK.name(),
                Material.REPEATING_COMMAND_BLOCK.name(),
                Material.FIRE.name(),
                Material.SOUL_FIRE.name(),
                Material.WATER.name(),
                Material.LAVA.name(),
                Material.NETHER_PORTAL.name(),
                Material.JIGSAW.name(),
                Material.MOVING_PISTON.name(),
                Material.END_PORTAL.name(),
                Material.BARRIER.name(),
                Material.PISTON_HEAD.name(),
                Material.OAK_WALL_SIGN.name(),
                Material.SPRUCE_WALL_SIGN.name(),
                Material.BIRCH_WALL_SIGN.name(),
                Material.ACACIA_WALL_SIGN.name(),
                Material.CHERRY_WALL_SIGN.name(),
                Material.JUNGLE_WALL_SIGN.name(),
                Material.DARK_OAK_WALL_SIGN.name(),
                Material.MANGROVE_WALL_SIGN.name(),
                Material.BAMBOO_WALL_SIGN.name(),
                Material.OAK_WALL_HANGING_SIGN.name(),
                Material.SPRUCE_WALL_HANGING_SIGN.name(),
                Material.BIRCH_WALL_HANGING_SIGN.name(),
                Material.ACACIA_WALL_HANGING_SIGN.name(),
                Material.CHERRY_WALL_HANGING_SIGN.name(),
                Material.JUNGLE_WALL_HANGING_SIGN.name(),
                Material.DARK_OAK_WALL_HANGING_SIGN.name(),
                Material.MANGROVE_WALL_HANGING_SIGN.name(),
                Material.CRIMSON_WALL_HANGING_SIGN.name(),
                Material.WARPED_WALL_HANGING_SIGN.name(),
                Material.BAMBOO_WALL_HANGING_SIGN.name(),
                Material.WATER_CAULDRON.name(),
                Material.LAVA_CAULDRON.name(),
                Material.POWDER_SNOW_CAULDRON.name(),
                Material.WHITE_WALL_BANNER.name(),
                Material.ORANGE_WALL_BANNER.name(),
                Material.MAGENTA_WALL_BANNER.name(),
                Material.LIGHT_BLUE_WALL_BANNER.name(),
                Material.YELLOW_WALL_BANNER.name(),
                Material.LIME_WALL_BANNER.name(),
                Material.PINK_WALL_BANNER.name(),
                Material.GRAY_WALL_BANNER.name(),
                Material.LIGHT_GRAY_WALL_BANNER.name(),
                Material.CYAN_WALL_BANNER.name(),
                Material.PURPLE_WALL_BANNER.name(),
                Material.BLUE_WALL_BANNER.name(),
                Material.BROWN_WALL_BANNER.name(),
                Material.GREEN_WALL_BANNER.name(),
                Material.RED_WALL_BANNER.name(),
                Material.BLACK_WALL_BANNER.name(),
                Material.FROSTED_ICE.name(),
                Material.DEAD_TUBE_CORAL_WALL_FAN.name(),
                Material.DEAD_BRAIN_CORAL_WALL_FAN.name(),
                Material.DEAD_BUBBLE_CORAL_WALL_FAN.name(),
                Material.DEAD_FIRE_CORAL_WALL_FAN.name(),
                Material.DEAD_HORN_CORAL_WALL_FAN.name(),
                Material.CRIMSON_WALL_SIGN.name(),
                Material.WARPED_WALL_SIGN.name(),
                Material.CANDLE_CAKE.name(),
                Material.WHITE_CANDLE_CAKE.name(),
                Material.ORANGE_CANDLE_CAKE.name(),
                Material.MAGENTA_CANDLE_CAKE.name(),
                Material.LIGHT_BLUE_CANDLE_CAKE.name(),
                Material.YELLOW_CANDLE_CAKE.name(),
                Material.LIME_CANDLE_CAKE.name(),
                Material.PINK_CANDLE_CAKE.name(),
                Material.GRAY_CANDLE_CAKE.name(),
                Material.LIGHT_GRAY_CANDLE_CAKE.name(),
                Material.CYAN_CANDLE_CAKE.name(),
                Material.PURPLE_CANDLE_CAKE.name(),
                Material.BLUE_CANDLE_CAKE.name(),
                Material.BROWN_CANDLE_CAKE.name(),
                Material.GREEN_CANDLE_CAKE.name(),
                Material.RED_CANDLE_CAKE.name(),
                Material.BLACK_CANDLE_CAKE.name(),
                Material.BAMBOO.name(),
                Material.IRON_BARS.name(),
                Material.CHAIN.name(),
                Material.GLASS_PANE.name(),
                Material.SCULK_VEIN.name(),
                Material.WHITE_STAINED_GLASS_PANE.name(),
                Material.ORANGE_STAINED_GLASS_PANE.name(),
                Material.MAGENTA_STAINED_GLASS_PANE.name(),
                Material.LIGHT_BLUE_STAINED_GLASS_PANE.name(),
                Material.YELLOW_STAINED_GLASS_PANE.name(),
                Material.LIME_STAINED_GLASS_PANE.name(),
                Material.PINK_STAINED_GLASS_PANE.name(),
                Material.GRAY_STAINED_GLASS_PANE.name(),
                Material.LIGHT_GRAY_STAINED_GLASS_PANE.name(),
                Material.CYAN_STAINED_GLASS_PANE.name(),
                Material.PURPLE_STAINED_GLASS_PANE.name(),
                Material.BLUE_STAINED_GLASS_PANE.name(),
                Material.BROWN_STAINED_GLASS_PANE.name(),
                Material.GREEN_STAINED_GLASS_PANE.name(),
                Material.RED_STAINED_GLASS_PANE.name(),
                Material.BLACK_STAINED_GLASS_PANE.name(),
                Material.TURTLE_HELMET.name(),
                Material.SNIFFER_EGG.name(),
                Material.CONDUIT.name(),
                Material.DEAD_BRAIN_CORAL.name(),
                Material.DEAD_BUBBLE_CORAL.name(),
                Material.DEAD_FIRE_CORAL.name(),
                Material.DEAD_HORN_CORAL.name(),
                Material.DEAD_TUBE_CORAL.name(),
                Material.DEAD_TUBE_CORAL_FAN.name(),
                Material.DEAD_BRAIN_CORAL_FAN.name(),
                Material.DEAD_BUBBLE_CORAL_FAN.name(),
                Material.DEAD_FIRE_CORAL_FAN.name(),
                Material.DEAD_HORN_CORAL_FAN.name(),
                Material.SCULK_SENSOR.name(),
                Material.CALIBRATED_SCULK_SENSOR.name(),
                Material.TRAPPED_CHEST.name(),
        };

        List<Material> blockMaterials = new ArrayList<>();
        for (Material material : Material.values()) {
            boolean excluded = false;

            for (String excludedBlock : excludedBlocks) {
                if (material.name().equals(excludedBlock)) {
                    excluded = true;
                    break;
                }
            }

            if (excluded) {
                continue;
            }

            if (material.isBlock() && !material.isAir() && material.isSolid()) {
                blockMaterials.add(material);
            }
        }
        return blockMaterials;
    }

    public String formatMaterialName(Material material) {
        String name = material.name().toLowerCase().replace('_', ' ');
        String[] words = name.split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return formattedName.toString().trim();
    }

    // Items
    public ItemBuilder itemSeekerWeapon() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.IRON_SWORD)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%gSeeker Weapon"))
                .setTag("weapon-seeker")
                .setLores(new ArrayList<>() {{
                    add("");
                    add(Format.processString("%tThis is your weapon as a seeker"));
                }});
    }

    public ItemBuilder itemNavigateToAdmin() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.CRAFTING_TABLE)
                .setAmount(1)
                .setName(Format.processString("%B&6Enter Admin Menu"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%b%tClick to enter the admin menu"));
                }});
    }

    public ItemBuilder itemEnterArenaSetup() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.BOOK)
                .setAmount(1)
                .setName(Format.processString(Format.processString("%n%BEnter Arena Setup Mode")))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tClick to enter arena setup mode"));
                    add(Format.processString("%tYour inventory and player state will"));
                    add(Format.processString("%tbe stored whilst you're in setup mode"));
                }});
    }

    public ItemBuilder itemArenaSetupSelection() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.STICK)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BBoundary Selection"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.boundarySelection")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tBoundary selection mode"));
                    add("");
                    add(Format.processString("%aLEFT %tclick to select the first point"));
                    add(Format.processString("%aRIGHT %tclick to select the second point"));
                }});
    }

    public ItemBuilder itemArenaSetupSetName() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.NAME_TAG)
                .setAmount(1)
                .setName(Format.processString("%n%BArena Name"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.arenaName")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the name of the arena"));
                }});
    }

    public ItemBuilder itemArenaSetupSign() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.OAK_SIGN)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BArena Sign"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.arenaSign")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the arena signs"));
                }});
    }

    public ItemBuilder itemArenaSetupBlocks() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.CHEST)
                .setAmount(1)
                .setName(Format.processString("%n%BArena Blocks"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.arenaBlocks")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the blocks for the arena"));
                }});
    }

    public ItemBuilder itemArenaSetupBlocksToggleSelectedOn() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.GREEN_WOOL)
                .setAmount(1)
                .setName(Format.processString("%n%BToggle Selected - %gON"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.toggleSelectedBlock")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tClick to show all blocks"));
                }});
    }

    public ItemBuilder itemArenaSetupBlocksToggleSelectedOff() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.RED_WOOL)
                .setAmount(1)
                .setName(Format.processString("%n%BToggle Selected - %eOFF"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.toggleSelectedBlock")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tClick to show selected blocks only"));
                }});
    }

    public ItemBuilder itemArenaSetupConfig() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BArena Config"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.arenaConfig")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tCancel the current arena setup"));
                }});
    }

    public ItemBuilder itemArenaSetupCancel() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.REDSTONE)
                .setAmount(1)
                .setName(Format.processString("%n%BCancel"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.cancelArenaSetup")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tCancel the current arena setup"));
                }});
    }

    public ItemBuilder itemArenaSetupSave() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.EMERALD)
                .setAmount(1)
                .setName(Format.processString("%n%BSave"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.saveArenaSetup")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSave the current arena setup"));
                }});
    }

    public ItemBuilder itemArenaSetupLobbySpawn() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.BLUE_WOOL)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BLobby Spawn"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.lobbySpawn")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the lobby spawn point"));
                }});
    }

    public ItemBuilder itemArenaSetupHiderSpawn() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.GREEN_WOOL)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BHider Spawn"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.hiderSpawn")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the hider spawn point"));
                }});
    }

    public ItemBuilder itemArenaSetupSeekerSpawn() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.RED_WOOL)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BSeeker Spawn"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.seekerSpawn")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the seeker spawn point"));
                }});
    }

    public ItemBuilder itemArenaSetupAfterGameSpawn() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.YELLOW_WOOL)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BAfter Game Spawn"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.afterGameSpawn")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the after game spawn point"));
                }});
    }

    public ItemBuilder itemArenaSetupActions() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.LEVER)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BActions"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.actions")

                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSelect an action to perform"));
                }});
    }

    public ItemBuilder itemArenaSetupParameters() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.BOOK)
                .setAmount(1)
                .setName(Format.processString("%n%BParameters"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.parameters")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet arena parameters"));
                }});
    }

    public ItemBuilder itemArenaSetupToolSelection() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.IRON_AXE)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BArena Tool Selection"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setAction("mchunt.setup.toolSelection")
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSelect the tool to use for arena setup"));
                }});
    }

    public ItemBuilder itemBackArrow() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.ARROW)
                .setAmount(1)
                .setName(Format.processString("%n%BBack"));
    }

    public ItemBuilder itemNextArrow() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.ARROW)
                .setAmount(1)
                .setName(Format.processString("%n%BNext"));
    }

    public ItemBuilder itemArenaSetupParametersMinimumPlayers() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BMinimum Players"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the minimum number of players"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersMaximumPlayers() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BMaximum Players"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the maximum number of players"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersSeekerCount() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BSeeker Count"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the number of seekers on game start"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersCountdownBeforeGameStart() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BCountdown Before Game Start"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the countdown before the game starts"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersCountdownAfterGameEnd() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BCountdown After Game End"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the countdown after the game ends"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersRespawnDelay() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BRespawn Delay"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the respawn delay"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersSeekerReleaseDelay() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BSeeker Release Delay"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet how many seconds before seekers are released"));
                }});
    }

    public ItemBuilder itemArenaSetupParametersGameLength() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.PAPER)
                .setAmount(1)
                .setName(Format.processString("%n%BGame Length"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tSet the game length"));
                }});
    }

    public ItemBuilder itemArenaSetupCreateArena() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.EMERALD)
                .setAmount(1)
                .setName(Format.processString("%n%BCreate Arena"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tCreate the arena"));
                }});
    }

    public ItemBuilder itemArenaSetupDiscardChanges() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.REDSTONE)
                .setAmount(1)
                .setName(Format.processString("%n%BDiscard Changes"))
                .setPermission(Permissions.PERMISSION_ADMIN.getPermission())
                .setLores(new ArrayList<String>() {{
                    add("");
                    add(Format.processString("%tDiscard all changes"));
                }});
    }

    public ItemBuilder itemArenaLeave() {
        return new ItemBuilder(this.plugin)
                .setMaterial(Material.RED_BED)
                .setAmount(1)
                .setDroppable(false)
                .setMovable(false)
                .setName(Format.processString("%n%BLeave Arena"))
                .setLores(new ArrayList<>() {{
                    add("");
                    add(Format.processString("%tClick to leave the arena"));
                }});
    }
}
