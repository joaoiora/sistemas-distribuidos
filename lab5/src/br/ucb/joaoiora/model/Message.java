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
    void append(final String message);

    /**
     *
     * @param message
     * @param appendLinebreak
     */
    void append(final String message, boolean appendLinebreak);

    /**
     *
     * @return
     */
    String getContent();
}
