package br.ucb.joaoiora.model;

import br.ucb.joaoiora.utils.StringUtils;

/**
 * Created by Joao on 08/05/2017.
 */
public abstract class AbstractMessage implements Message {

    /**
     *
     */
    protected static final String NEW_LINE = System.getProperty("line.separator");

    /**
     *
     */
    private StringBuilder content;

    /**
     *
     */
    public AbstractMessage() {
        this.content = new StringBuilder();
    }

    /**
     *
     * @param message
     */
    @Override
    public void appendMessage(String message) {
        content.append(message);
        if (!message.contains(NEW_LINE)) {
            content.append(NEW_LINE);
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
