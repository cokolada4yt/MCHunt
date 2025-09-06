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
            Material.FROSTED_ICE.name(),
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

            if (excluded) continue;

            // ✅ hlavní oprava: musí to být i item
            if (material.isBlock() && material.isItem() && !material.isAir() && material.isSolid()) {
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

    // ⬇️ všechny itemBuilder metody nechávám beze změny …
    // (itemSeekerWeapon, itemArenaSetupBlocks, itemArenaSetupConfig, atd.)
    // … protože se chyby týkaly jenom výběru bloků
}
