package me.endergaming.mypronoun.inventorygui;

import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.MyPronoun;
import me.endergaming.mypronoun.storage.Pronoun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectionGUI {
    private final MyPronoun plugin;
    public static String inventory_name;
    public static int rows = 3;
    public static int col = 9;

    public SelectionGUI(@NotNull final MyPronoun instance) {
        this.plugin = instance;
        initialize();
    }

    public void initialize() {
        inventory_name = MessageUtils.colorize("&9Pronoun Selection");
    }

    public Inventory GUI(int activePronoun) {
        Inventory inv = Bukkit.createInventory(null, col * rows, inventory_name);
        // Create the inventory
        // Add pronouns
        for (Pronoun pronoun : plugin.getConfigController().getPronouns()) {
            // Check if position is outside of inventory
            if (pronoun.getPosition() > col * rows) continue;
            ItemStack itemStack = pronoun.getGUIItem();
            if (activePronoun != -1 && activePronoun == pronoun.getId()) {
                ItemMeta meta = itemStack.getItemMeta();
                if (meta == null) continue;
                meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(meta);
            }
            placeItem(inv, pronoun.getPosition(), itemStack);
        }
        // Fill the rest of the inventory
        fillInventory(inv, createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " "), findEmptySlots(inv));
        inv.setContents((inv.getContents()));
        return inv;
    }

    public void placeItem(Inventory inv, int invSlot, ItemStack item) {
        inv.setItem(invSlot, item);
    }

    public void fillInventory(Inventory inv, ItemStack item, List<Integer> slots) {
        for (int slot : slots) {
            inv.setItem(slot, item);
        }
    }

    public void fillInventory(Inventory inv, List<ItemStack> items) {
        for (int i = 0; i < items.size(); ++i) {
            inv.setItem(i, items.get(i));
        }
    }

    public ItemStack createItem(Material material, int amount, String displayName, String... loreString) { // With Lore
        ItemStack item;
        List<String> lore = new ArrayList();
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        for (String s : loreString) {
            lore.add(s);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createItem(Material material, int amount, String displayName) { // With No Lore
        ItemStack item;
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    public List<Integer> findEmptySlots (Inventory inv) {
        List<Integer> slots = new ArrayList<>();
        for (int place = 0; place < inv.getSize(); ++place) {
            if (inv.getItem(place) == null) slots.add(place);
        }
        return slots;
    }
}
