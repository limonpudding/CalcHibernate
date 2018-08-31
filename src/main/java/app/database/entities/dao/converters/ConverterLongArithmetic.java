package app.database.entities.dao.converters;

import app.math.LongArithmethic;
import app.math.LongArithmeticImplList;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ConverterLongArithmetic implements AttributeConverter<LongArithmethic,String> {

    @Override
    public String convertToDatabaseColumn(LongArithmethic attribute) {
        return attribute.toString();
    }

    @Override
    public LongArithmethic convertToEntityAttribute(String dbData) {
        try {
            return new LongArithmeticImplList(dbData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
