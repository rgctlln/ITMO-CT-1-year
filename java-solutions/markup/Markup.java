package markup;

public interface Markup {
    void toMarkdown(StringBuilder builder);
    void toBBCode(StringBuilder builder);
}
