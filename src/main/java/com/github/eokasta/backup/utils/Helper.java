package com.github.eokasta.backup.utils;

import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static String format(String s) { return ChatColor.translateAlternateColorCodes('&', s); }

    public static String format(String s, String... args) { return format(String.format(s, args)); }

    public static List<String> format(List<String> s) { return s.stream().map(Helper::format).collect(Collectors.toList()); }

    public static List<String> format(List<String> s, String... args) { return s.stream().map(string -> format(string, args)).collect(Collectors.toList()); }

    public static String getDate(Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        return formatter.format(date);
    }

}
