package hw6.visitors;

import hw6.tokens.BracketToken;
import hw6.tokens.NumberToken;
import hw6.tokens.OperationToken;

public class PrintVisitor extends TokenVisitor<String> {
    private final StringBuilder debuilder;

    public PrintVisitor() {
        this.debuilder = new StringBuilder();
    }

    @Override
    public void visit(NumberToken number) {
        debuilder.append(number.getValue()).append(" ");
    }

    @Override
    public void visit(BracketToken bracket) {
        throw new UnsupportedOperationException("Can't print bracket in polish");
    }

    @Override
    public void visit(OperationToken operation) {
        debuilder.append(operation.getSymbol()).append(" ");
    }

    @Override
    public String result() {
        return debuilder.toString();
    }
}
