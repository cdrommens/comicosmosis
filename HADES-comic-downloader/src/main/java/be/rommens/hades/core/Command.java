package be.rommens.hades.core;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 15:41
 */
public interface Command {

    boolean execute();
    boolean rollback();
}
