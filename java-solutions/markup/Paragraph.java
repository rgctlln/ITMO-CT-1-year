package markup;

import java.util.List;

public class Paragraph implements Markup {
    private final List<Markup> list;

    public Paragraph(List<Markup> list) {
        this.list = list;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        for (Markup strings : list) {
            strings.toMarkdown(builder);
        }
    }

    @Override
    public void toBBCode(StringBuilder builder) {
        for (Markup strings : list) {
            strings.toBBCode(builder);
        }
    }
}
