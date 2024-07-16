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
    void normalStringOfComposition() {
        String translated = materialProcess.generateCompositionString("50% CO 40% WO 10% PA");
        Assertions.assertEquals("50% бавовна 40% вовна 10% поліамід", translated);
    }

    @DisplayName("Test summing with same fibres")
    @Test
    void sumSameFibres() {
        String translated = materialProcess.generateCompositionString("50% CO 20% WO 20% WV 10% PA");
        Assertions.assertEquals("50% бавовна 40% вовна 10% поліамід", translated);
    }

    @DisplayName("Test with unsorted percentage")
    @Test
    void unsortedPercentage() {
        String translated = materialProcess.generateCompositionString("10% WO 50% CO 40% PA");
        Assertions.assertEquals("50% бавовна 40% поліамід 10% вовна", translated);
    }
    @DisplayName("Test with multiple Composition with lining")
    @Test
    void multipleCompostionWithLiningsTest() {
        String translated = materialProcess.generateCompositionString("Fabric: 51% CO 49% VI Lining 51% WO 49% CO");
        Assertions.assertEquals("51% бавовна 49% віскоза", translated);
    }
    @DisplayName("Test with multiple Composition with lining:")
    @Test
    void multipleCompostionWithLiningsDoubleDotsTest() {
        String translated = materialProcess.generateCompositionString("Fabric: 51% CO 49% VI Lining: 51% WO 49% CO");
        Assertions.assertEquals("51% бавовна 49% віскоза", translated);
    }
    @DisplayName("Test Compositions without percentage")
    @Test
    void compositionTestWithoutPercentage() {
        String translated = materialProcess.generateCompositionString("Fabric: 51 CO 49 VI Lining: 51 WO 49 CO");
        Assertions.assertEquals("51% бавовна 49% віскоза", translated);
    }

    @Test
    void test(){
        String translated = materialProcess.generateCompositionString("Knitted fabric: 35% Polyamide, 30% Viscose, 30% Wool Ovis aries aries, 05% Cashmere Capra hircus hircus CINA\n");
        Assertions.assertEquals("35% поліамід 35% вовна 30% віскоза", translated);
    }

    @Nested
    class TranslateMaterial {
        @DisplayName("Test with translation")
        @Test
        void translateMaterialTest() {
            String translated = materialProcess.translateComposition("CO");
            Assertions.assertEquals("бавовна", translated);
            translated = materialProcess.translateComposition("WO");
            Assertions.assertEquals("вовна", translated);
        }

    }
}
