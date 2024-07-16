package markup;

import java.util.List;

public class Strikeout extends AbstractMark implements Markup {
    @Override
    public String getWrap() {
        return "~";
    }

    @Override
    protected String getBBWrapStart() {
        return "[s]";
    }

    @Override
    protected String getBBWrapEnd() {
        return "[/s]";
    }

    public Strikeout(List<Markup> list) {
        super(list);
    }
}
