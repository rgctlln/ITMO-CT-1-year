package markup;

import java.util.List;

public abstract class AbstractMark implements Markup {
    private final List<Markup> list;


    public AbstractMark(List<Markup> list) {
        this.list = list;
    }

    protected abstract String getWrap();
    protected abstract String getBBWrapStart();
    protected abstract String getBBWrapEnd();

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(getWrap());
        for (Markup strings : list) {
            strings.toMarkdown(builder);
        }
        builder.append(getWrap());
    }
    @Override
    public void toBBCode(StringBuilder builder) {
        builder.append(getBBWrapStart());
        for (Markup strings : list) {
            strings.toBBCode(builder);
        }
        builder.append(getBBWrapEnd());
    }
}
