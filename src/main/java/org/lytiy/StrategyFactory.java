package org.lytiy;

import org.lytiy.strategy.*;

public class StrategyFactory {
    public static IExcelProcessingStrategy getStrategy(Operation operation, ExcelProcessingContext context) {
        return switch (operation) {
            case COLLECT_PRODUCTS -> new ExcelProductReadingStrategy(context.getProductReader());
            case PROCESS_PRODUCTS -> new ExcelProductProcessStrategy(context.getMaterialProcess());
            case SORT_PRODUCT_COMPOSITION -> new ExcelSortCompositionStrategy(context.getMaterialProcess());
            case SAVE_RESULTS -> new ExcelProductWriteStrategy(context.getProductWriter());
            case PROCESS_PRODUCT_HS_CODE -> new ExcelProductCommoditySpecifyGender(context.getCommodityProcess());
            case TRANSLATE_PRODUCT_COUNTRY -> new ExcelProductTranslateCountryOrigin(context.getCountryProcess());
            default -> null;
        };
    }
}

