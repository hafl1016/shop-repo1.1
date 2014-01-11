package de.shop.kundenverwaltung.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class FamilienstandTypeConverter implements AttributeConverter<FamilienstandType, String> {
	@Override
	public String convertToDatabaseColumn(FamilienstandType familienstandType) {
		if (familienstandType == null) {
			return null;
		}
		return familienstandType.getInternal();
	}

	@Override
	public FamilienstandType convertToEntityAttribute(String internal) {
		return FamilienstandType.build(internal);
	}
}
