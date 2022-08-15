package me.endergaming.mypronoun.storage;

import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Pronoun {
    protected final int id;
    protected int position;
    protected String color;
    protected String pronoun;
    protected Material display;

    public Pronoun(int id, @NotNull String pronoun, @NotNull Material display, @NotNull String color, int position) {
        this.id = id;
        this.pronoun = pronoun;
        this.display = display;
        this.color = color;
        this.position = position;
    }

    public ItemStack getGUIItem() {
        ItemStack itemStack = new ItemStack(display);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> matLore = Arrays.asList(" ", MessageUtils.color("&dDo you prefer as &n" + pronoun + "&d?"));

        if (meta != null) {
            meta.getPersistentDataContainer().set(JavaPlugin.getPlugin(MyPronoun.class).getPronounKey(), PersistentDataType.INTEGER, id);
            meta.setDisplayName(MessageUtils.color(color + "&l" + pronoun));
            meta.setLore(matLore);
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public String getPronoun() {
        return pronoun;
    }

    public int getId() {
        return id;
    }

    public Material getDisplay() {
        return display;
    }

    public String getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pronoun{" +
                "id=" + id +
                ", position=" + position +
                ", color='" + color + '\'' +
                ", pronoun='" + pronoun + '\'' +
                ", display=" + display +
                '}';
    }
}
