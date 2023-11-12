package dev.oflords.realregions.util.menu.pagination;

import dev.oflords.realregions.util.menu.Button;
import dev.oflords.realregions.util.menu.Menu;
import org.bukkit.entity.Player;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

public class ViewAllPagesMenu extends Menu {

    public ViewAllPagesMenu(PaginatedMenu menu) {
        this.menu = menu;
    }

    PaginatedMenu menu;

    @Override
    public String getTitle(Player player) {
        return "&bJump to page";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new BackButton(menu));

        int index = 10;

        for (int i = 1; i <= menu.getPages(player); i++) {
            buttons.put(index++, new JumpToPageButton(i, menu, menu.getPage() == i));

            if ((index - 8) % 9 == 0) {
                index += 2;
            }
        }

        return buttons;
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    public PaginatedMenu getMenu() {
        return menu;
    }
}
