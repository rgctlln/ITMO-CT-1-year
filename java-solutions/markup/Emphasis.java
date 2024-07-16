package markup;

import java.util.List;

public class Emphasis extends AbstractMark implements Markup {
    @Override
    protected String getWrap() {
        return "*";
    }

    @Override
    protected String getBBWrapStart() {
        return "[i]";
    }

    @Override
    protected String getBBWrapEnd() {
        return "[/i]";
    }

    public Emphasis(List<Markup> list) {
        super(list);
    }
}
