/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.remote.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import student.remote.login.Permission;

/**
 *
 * @author haro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RemoteVisibility {
    public Permission value();
}
