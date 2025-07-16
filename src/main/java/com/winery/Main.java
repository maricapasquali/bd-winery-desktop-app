package com.winery;

import com.winery.View.Personalmenu.AbstractFrameDefault;
import com.winery.View.Personalmenu.MenuLogin;

import javax.swing.*;

public class Main {
    public static void main(final String[] arg) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            final AbstractFrameDefault f = new MenuLogin();
            f.display();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
    }
}
