import org.example.Product.ProductProcess.Composition.MaterialProcess;
import org.example.Product.ProductProcess.Composition.MaterialProcessImpl;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class MaterialProcessTest {
    static MaterialProcess materialProcess;
    @BeforeAll
    static void setup() throws IOException {
        materialProcess = new MaterialProcessImpl();
    }
    @DisplayName("Test with normal string")
    @Test
    void normalStringOfComposition(){
        String translated = materialProcess.generateCompositionString("50% CO 40% WO 10% PA");
        Assertions.assertEquals("50% бавовна 40% вовна 10% поліамід", translated);
    }
    @DisplayName("Test summing with same fibres")
    @Test
    void sumSameFibres(){
        String translated = materialProcess.generateCompositionString("50% CO 20% WO 20% WV 10% PA");
        Assertions.assertEquals("50% бавовна 40% вовна 10% поліамід", translated);
    }

    @DisplayName("Test with unsorted percentage")
    @Test
    void unsortedPercentage(){
        String translated = materialProcess.generateCompositionString("10% WO 50% CO 40% PA");
        Assertions.assertEquals("50% бавовна 40% поліамід 10% вовна", translated);
    }

    @Nested
    class TranslateMaterial {
        @DisplayName("Test with translation")
        @Test
        void translateMaterialTest(){
            String translated = materialProcess.translateComposition("CO");
            Assertions.assertEquals("бавовна", translated);
            translated = materialProcess.translateComposition("WO");
            Assertions.assertEquals("вовна", translated);
        }

    }
}
