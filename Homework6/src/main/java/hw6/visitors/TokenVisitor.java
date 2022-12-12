package hw6.visitors;

import hw6.tokens.BracketToken;
import hw6.tokens.NumberToken;
import hw6.tokens.OperationToken;
import hw6.tokens.Token;

import java.util.List;

public abstract class TokenVisitor<T> {
    public abstract void visit(NumberToken number);

    public abstract void visit(BracketToken bracket);

    public abstract void visit(OperationToken operation);

    public abstract T result();

    public T process(List<Token> tokens) {
        for (var token : tokens) {
            token.accept(this);
        }
        return result();
    }
}
