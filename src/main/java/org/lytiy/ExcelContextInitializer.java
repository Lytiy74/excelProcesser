package org.lytiy;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lytiy.cargo.product.productprocess.ProductMeta;
import org.lytiy.cargo.product.productprocess.category.ProductCategorizer;
import org.lytiy.cargo.product.productprocess.commodity.CommodityItem;
import org.lytiy.cargo.product.productprocess.commodity.ICommodityProcess;
import org.lytiy.cargo.product.productprocess.commodity.ProductCommodityProcess;
import org.lytiy.cargo.product.productprocess.composition.IMaterialProcess;
import org.lytiy.cargo.product.productprocess.composition.MaterialProcessImpl;
import org.lytiy.cargo.product.productprocess.countryOrigin.CountryProcessImpl;
import org.lytiy.cargo.product.productprocess.countryOrigin.ICountryProcess;
import org.lytiy.excelprocessor.*;
import org.lytiy.strategy.ExcelProcessingContext;
import org.lytiy.util.ITranslator;
import org.lytiy.util.MapConverter;
import org.lytiy.util.Translator;
import org.lytiy.util.io.CsvFileReader;
import org.lytiy.util.io.JsonFileReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import static org.lytiy.Main.DEFAULT_SHEET_INDEX;
import static org.lytiy.ResourceFiles.*;

public class ExcelContextInitializer {
    public static ExcelProcessingContext initialize(Path inputFilePath, Path jarDir, JsonFileReader jsonFileReader, CsvFileReader csvFileReader) throws IOException {
        HashMap<String, String> countryTranslationMap = csvFileReader.read(jarDir.resolve(COUNTRY_TRANSLATIONS_CSV_FILE.getFileName()).toString());
        HashMap<String, String> materialCompositionMap = MapConverter.invertColumnMap(jsonFileReader.readJsonObjectArrayToMap(jarDir.resolve(COMPOSITION_JSON_FILE.getFileName()).toString()));
        HashMap<String, List<String>> targetColumnsMap = jsonFileReader.readJsonObjectArrayToMap(jarDir.resolve(COLUMN_NAME_JSON_FILE.getFileName()).toString());
        HashMap<String, ProductMeta> metas = jsonFileReader.readProductMetaJsonToHashMap(jarDir.resolve(CLOTHES_NAMES_JSON_FILE.getFileName()).toString());
        HashMap<String, CommodityItem> harmonizedCodes = jsonFileReader.readJsonToHashMap(jarDir.resolve(HARMONIZED_CODES_JSON_FILE.getFileName()).toString());

        try (Workbook workbook = new XSSFWorkbook(inputFilePath.toString()); Workbook outWorkbook = new XSSFWorkbook()) {
            IExcelColumnIdentifier columnIdentifier = new ExcelColumnIdentifierImpl();
            Sheet sheet = workbook.getSheetAt(DEFAULT_SHEET_INDEX);
            int headRowIndex = columnIdentifier.findAndGetNumberOfHeaderRow(sheet, targetColumnsMap);
            ProductCategorizer productCategorizer = new ProductCategorizer(metas);
            HashMap<String, Integer> identifiedColumns = columnIdentifier.identifyColumns(sheet.getRow(headRowIndex), targetColumnsMap);
            ITranslator translatorForCountries = new Translator(countryTranslationMap);
            ITranslator translatorForMaterial = new Translator(materialCompositionMap);
            IExcelProductBuilder productBuilder = new ExcelProductBuilder(new ExcelCellValueExtractorImpl(), identifiedColumns, productCategorizer);
            IExcelProductReader productReader = new ExcelProductReader(productBuilder, headRowIndex);
            IExcelProductWriter productWriter = new ExcelProductWriter(targetColumnsMap.keySet().stream().toList());
            ICountryProcess countryProcess = new CountryProcessImpl(translatorForCountries);
            IMaterialProcess materialProcess = new MaterialProcessImpl(translatorForMaterial);
            ICommodityProcess commodityProcess = new ProductCommodityProcess(harmonizedCodes);

            return new ExcelProcessingContext.Builder()
                    .workbook(workbook)
                    .outWorkbook(outWorkbook)
                    .sheet(sheet)
                    .targetColumns(targetColumnsMap)
                    .identifiedColumns(identifiedColumns)
                    .productBuilder(productBuilder)
                    .productReader(productReader)
                    .productWriter(productWriter)
                    .materialProcess(materialProcess)
                    .commodityProcess(commodityProcess)
                    .countyProcess(countryProcess)
                    .build();
        }
    }
}
