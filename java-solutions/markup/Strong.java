package markup;

import java.util.List;

public class Strong extends AbstractMark implements Markup {
    @Override
    protected String getWrap() {
        return "__";
    }

    @Override
    protected String getBBWrapStart() {
        return "[b]";
    }

    @Override
    protected String getBBWrapEnd() {
        return "[/b]";
    }

    public Strong(List<Markup> list) {
        super(list);
    }
}
