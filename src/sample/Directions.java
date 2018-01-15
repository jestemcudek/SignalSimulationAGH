package sample;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Damian on 15.01.2018.
 */
public enum Directions {
    South,North,East,West;

    private static final List<Directions> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Directions randomDirection()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
