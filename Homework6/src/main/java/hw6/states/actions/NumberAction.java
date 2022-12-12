package hw6.states.actions;

import hw6.states.TokenStates;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@RequiredArgsConstructor
public class NumberAction implements Action<TokenStates, Character> {
    private final StringBuilder number;

    @Override
    public void execute(StateContext<TokenStates, Character> context) {
        number.append(context.getEvent());
    }
}
