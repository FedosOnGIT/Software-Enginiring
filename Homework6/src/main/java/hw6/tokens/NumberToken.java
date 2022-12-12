package hw6.tokens;

import hw6.visitors.TokenVisitor;
import lombok.Getter;

@Getter
public class NumberToken implements Token {
    private final Integer number;
    private final String value;

    public NumberToken(String value) {
        this.value = value;
        this.number = Integer.parseInt(value);
    }

    @Override
    public void accept(TokenVisitor<?> visitor) {
        visitor.visit(this);
    }

}
