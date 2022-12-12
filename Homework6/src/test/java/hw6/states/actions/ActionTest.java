package hw6.states.actions;

import hw6.states.TokenStates;
import hw6.tokens.BracketToken;
import hw6.tokens.NumberToken;
import hw6.tokens.OperationToken;
import hw6.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionTest {
    StateContext<TokenStates, Character> context;

    @BeforeEach
    public void init() {
        context = mock(StateContext.class);
    }

    @Test
    public void bracketActionTest() {
        when(context.getEvent()).thenReturn('(');
        List<Token> tokens = new ArrayList<>();
        var action = new BracketAction(tokens);
        action.execute(context);

        assertEquals(1, tokens.size());
        assertEquals('(', ((BracketToken) tokens.get(0)).bracket());
    }

    @Test
    public void errorActionTest() {
        StringBuilder number = new StringBuilder("test");
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken("1"));

        var action = new ErrorAction(number, tokens);
        action.execute(context);

        assertEquals(0, number.length());
        assertEquals(0, tokens.size());
    }

    @Test
    public void numberActionTest() {
        when(context.getEvent()).thenReturn('1');

        StringBuilder number = new StringBuilder();
        var action = new NumberAction(number);
        action.execute(context);

        assertEquals(1, number.length());
        assertEquals("1", number.toString());
    }

    @Test
    public void operationActionTest() {
        when(context.getEvent()).thenReturn('+');

        List<Token> tokens = new ArrayList<>();
        var action = new OperationAction(tokens);
        action.execute(context);

        assertEquals(1, tokens.size());
        OperationToken token = (OperationToken) tokens.get(0);
        assertEquals('+', token.getSymbol());
        assertEquals(1, token.getPriority());
        assertEquals(3, token.getOperation().apply(1, 2));
    }

    @Test
    public void processNumberActionTest() {
        StringBuilder number = new StringBuilder("228");
        List<Token> tokens = new ArrayList<>();

        var action = new ProcessNumberAction(number, tokens);
        action.execute(context);

        assertEquals(0, number.length());
        assertEquals(1, tokens.size());

        NumberToken token = (NumberToken) tokens.get(0);

        assertEquals("228", token.getValue());
        assertEquals(228, token.getNumber());
    }
}
