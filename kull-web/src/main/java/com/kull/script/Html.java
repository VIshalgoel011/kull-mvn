/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.script;

import java.text.MessageFormat;

/**
 *
 * @author lin
 */
public class Html{
    
            public static String fieldset(String legend, String context){
		return MessageFormat.format("<fieldset><legend>{0}</legend>{1}</fieldset>", legend,context);
	}
}
