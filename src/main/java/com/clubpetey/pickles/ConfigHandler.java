package com.clubpetey.pickles;

import java.io.File;

import net.minecraftforge.common.config.Configuration;


public class ConfigHandler {
	public static Configuration config;
	
	public static void init(File file) {
        config = new Configuration(file);
        
        if (config.hasChanged()) {
            config.save();
        }
	}

}
