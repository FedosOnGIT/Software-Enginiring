package hw6.states.actions;

import hw6.states.TokenStates;
import hw6.tokens.NumberToken;
import hw6.tokens.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.List;

@RequiredArgsConstructor
public class ProcessNumberAction implements Action<TokenStates, Character> {
    private final StringBuilder number;
    private final List<Token> tokens;

    @Override
    public void execute(StateContext<TokenStates, Character> context) {
        tokens.add(new NumberToken(number.toString()));
        number.setLength(0);
    }
}
