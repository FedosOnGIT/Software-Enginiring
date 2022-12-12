package hw6.config;

import hw6.states.TokenStates;
import hw6.states.actions.BracketAction;
import hw6.states.actions.ErrorAction;
import hw6.states.actions.NumberAction;
import hw6.states.actions.OperationAction;
import hw6.states.actions.ProcessNumberAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.List;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class TokenStateMachineConfig extends StateMachineConfigurerAdapter<TokenStates, Character> {
    private final List<Character> operations = List.of('+', '-', '/', '*');
    private final List<Character> skips = List.of(' ', '\t');

    private final BracketAction bracketAction;
    private final NumberAction numberAction;
    private final OperationAction operationAction;
    private final ProcessNumberAction processNumberAction;
    private final ErrorAction errorAction;

    @Override
    public void configure(StateMachineConfigurationConfigurer<TokenStates, Character> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<TokenStates, Character> states) throws Exception {
        states
                .withStates()
                .initial(TokenStates.START)
                .end(TokenStates.EOF)
                .states(EnumSet.allOf(TokenStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TokenStates, Character> transitions) throws Exception {
        //region Обрабатываем открывающую скобочку

        transitions
                .withExternal()
                .source(TokenStates.START)
                .target(TokenStates.START)
                .event('(')
                .action(bracketAction)

                .and()

                .withExternal()
                .source(TokenStates.OPERATION)
                .target(TokenStates.START)
                .event('(')
                .action(bracketAction);
        //endregion

        //region Обрабатываем число

        for (int i = 0; i <= 9; i++) {
            Character event = (char) (i + '0');
            transitions
                    .withExternal()
                    .source(TokenStates.START)
                    .target(TokenStates.DIGIT)
                    .event(event)
                    .action(numberAction)

                    .and()

                    .withExternal()
                    .source(TokenStates.OPERATION)
                    .target(TokenStates.DIGIT)
                    .event(event)
                    .action(numberAction)

                    .and()

                    .withExternal()
                    .source(TokenStates.DIGIT)
                    .target(TokenStates.DIGIT)
                    .event(event)
                    .action(numberAction);
        }
        //endregion

        //region Обрабатываем операцию

        for (var operation : operations) {
            transitions
                    .withExternal()
                    .source(TokenStates.END)
                    .target(TokenStates.OPERATION)
                    .event(operation)
                    .action(operationAction)

                    .and()

                    .withExternal()
                    .source(TokenStates.DIGIT)
                    .target(TokenStates.OPERATION)
                    .event(operation)
                    .action(processNumberAction)
                    .action(operationAction);
        }
        //endregion

        //region Обработка пробелов

        for (var skip : skips) {
            transitions
                    .withExternal()
                    .source(TokenStates.START)
                    .target(TokenStates.START)
                    .event(skip)

                    .and()

                    .withExternal()
                    .source(TokenStates.DIGIT)
                    .target(TokenStates.END)
                    .event(skip)
                    .action(processNumberAction)

                    .and()

                    .withExternal()
                    .source(TokenStates.OPERATION)
                    .target(TokenStates.OPERATION)
                    .event(skip)

                    .and()

                    .withExternal()
                    .source(TokenStates.END)
                    .target(TokenStates.END)
                    .event(skip);
        }
        //endregion

        //region Закрывающая скобка

        transitions
                .withExternal()
                .source(TokenStates.END)
                .target(TokenStates.END)
                .event(')')
                .action(bracketAction)

                .and()

                .withExternal()
                .source(TokenStates.DIGIT)
                .target(TokenStates.END)
                .event(')')
                .action(processNumberAction)
                .action(bracketAction);

        //endregion

        //region Ошибка, символ - ?

        transitions
                .withExternal()
                .source(TokenStates.START)
                .target(TokenStates.ERROR)
                .event('?')
                .action(errorAction)

                .and()

                .withExternal()
                .source(TokenStates.OPERATION)
                .target(TokenStates.ERROR)
                .event('?')
                .action(errorAction)

                .and()

                .withExternal()
                .source(TokenStates.DIGIT)
                .target(TokenStates.ERROR)
                .event('?')
                .action(errorAction)

                .and()

                .withExternal()
                .source(TokenStates.END)
                .target(TokenStates.ERROR)
                .event('?')
                .action(errorAction);
        //endregion

        //region End, символ \n

        transitions
                .withExternal()
                .source(TokenStates.END)
                .target(TokenStates.EOF)
                .event('\n')

                .and()

                .withExternal()
                .source(TokenStates.DIGIT)
                .target(TokenStates.EOF)
                .event('\n')
                .action(processNumberAction);
        //endregion
    }
}
