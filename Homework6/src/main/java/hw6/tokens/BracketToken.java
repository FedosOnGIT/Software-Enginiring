package hw6.tokens;

import hw6.visitors.TokenVisitor;

public record BracketToken(Character bracket) implements Token {
    @Override
    public void accept(TokenVisitor<?> visitor) {
        visitor.visit(this);
    }
}
