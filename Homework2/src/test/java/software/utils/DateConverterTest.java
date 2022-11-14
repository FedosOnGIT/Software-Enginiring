package software.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateConverterTest {
    @Test
    public void convertTest() {
        List<Long> times = DateConverter.convertTime(2);
        assertEquals(3, times.size());
    }
}
