package br.com.cadastrovagas.domain.enumerator;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SiteVagaEnumConverter implements AttributeConverter<SiteVagaEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(SiteVagaEnum siteVagaEnum) {
		if (siteVagaEnum == null) {
			return null;
		}
		return siteVagaEnum.getCodigo();
	}

	@Override
	public SiteVagaEnum convertToEntityAttribute(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		return SiteVagaEnum.obterPorCodigo(codigo);
	}
}
