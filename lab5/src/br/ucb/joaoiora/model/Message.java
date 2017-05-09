package br.ucb.joaoiora.model;

import java.io.Serializable;

/**
 * Created by joaocarlos on 08/05/17.
 */
public interface Message extends Serializable {

    /**
     *
     * @param message
     */
    public abstract void append(final String message);

    /**
     *
     * @return
     */
    public abstract String getContent();

}
