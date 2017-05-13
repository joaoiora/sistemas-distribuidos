package br.ucb.joaoiora.model;

import br.ucb.joaoiora.utils.StringUtils;

/**
 * Created by Joao on 08/05/2017.
 */
public abstract class AbstractMessage implements Message {

    /**
     *
     */
    protected static final String NEW_LINE = System.lineSeparator();

    /**
     *
     */
    private final StringBuilder content;

    /**
     *
     */
    public AbstractMessage() {
        this.content = new StringBuilder();
    }

    public AbstractMessage(String initialContent) {
        this();
        append(initialContent, false);
    }

    /**
     *
     * @param message
     */
    @Override
    public void append(String message) {
        content.append(message);
        /*
        if (!message.endsWith(NEW_LINE)) {
            content.append(NEW_LINE);
        }
        */
    }

    /**
     *
     * @param message
     * @param appendLinebreak
     */
    public void append(String message, boolean appendLinebreak) {
        append(message);
        if (appendLinebreak) {
            append(NEW_LINE);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getContent() {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return content.toString();
    }


}
