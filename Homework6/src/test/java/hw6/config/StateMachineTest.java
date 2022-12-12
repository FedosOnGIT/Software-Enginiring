package hw6.config;

import hw6.states.TokenStates;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StateMachineTest {
    @Autowired
    private StateMachineFactory<TokenStates, Character> factory;

    @Test
    @SneakyThrows
    public void normalExecutionTest() {
        var machine = factory.getStateMachine();
        StateMachineTestPlan<TokenStates, Character> plan =
                StateMachineTestPlanBuilder
                        .<TokenStates, Character>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectState(TokenStates.START)

                        .and()

                        .step()
                        .sendEvent('2')
                        .expectState(TokenStates.DIGIT)
                        .expectStateChanged(1)

                        .and()

                        .step()
                        .sendEvent('2')
                        .expectState(TokenStates.DIGIT)
                        .expectStateChanged(1)

                        .and()

                        .step()
                        .sendEvent('\t')
                        .expectState(TokenStates.END)
                        .expectStateChanged(1)

                        .and()

                        .step()
                        .sendEvent('+')
                        .expectState(TokenStates.OPERATION)
                        .expectStateChanged(1)

                        .and()

                        .step()
                        .sendEvent('1')
                        .expectState(TokenStates.DIGIT)
                        .expectStateChanged(1)

                        .and()

                        .step()
                        .sendEvent('\n')
                        .expectState(TokenStates.EOF)
                        .expectStateChanged(1)

                        .and().build();
        plan.test();
    }

    @Test
    @SneakyThrows
    public void incorrectEvents() {
        var machine = factory.getStateMachine();
        StateMachineTestPlan<TokenStates, Character> plan =
                StateMachineTestPlanBuilder
                        .<TokenStates, Character>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectState(TokenStates.START)

                        .and()

                        .step()
                        .sendEvent('+')
                        .expectEventNotAccepted(1)

                        .and()

                        .step()
                        .sendEvent('(')
                        .sendEvent('+')
                        .expectEventNotAccepted(1)

                        .and()

                        .step()
                        .sendEvent('2')
                        .sendEvent('+')
                        .sendEvent(')')
                        .expectEventNotAccepted(1)

                        .and()

                        .step()
                        .sendEvent('?')
                        .expectState(TokenStates.ERROR)
                        .expectStateChanged(1)

                        .and().build();
        plan.test();
    }
}
