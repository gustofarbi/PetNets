import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar {


    static void makeMenuBar(MainFrame frame){
        JMenuItem menuItem;
        JMenuBar menuBar;
        JMenu menu;

        menuBar = new JMenuBar();
        menu = new JMenu("File");

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(new MenuItemListener());
        menuItem.setActionCommand("new");
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new MenuItemListener());
        menuItem.setActionCommand("open");
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new MenuItemListener());
        menuItem.setActionCommand("save");
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as");
        menuItem.addActionListener(new MenuItemListener());
        menuItem.setActionCommand("save as");
        menu.add(menuItem);

        menuItem = new JMenuItem("Close project");
        menuItem.addActionListener(new MenuItemListener());
        menuItem.setActionCommand("close");
        menu.add(menuItem);

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }
}
class MenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "new":
                break;
            case "open":
                //openFile();
                break;
            case "save":
                //saveFile();
                break;
            case "save as":
                //saveFileAs();
                break;
            case "close":
                //close();
                break;
        }
    }
}
