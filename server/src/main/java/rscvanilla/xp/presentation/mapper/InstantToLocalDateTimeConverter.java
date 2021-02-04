package rscvanilla.xp.presentation.mapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import rscvanilla.xp.domain.utils.DateTime;

import java.time.Instant;
import java.time.LocalDateTime;

public class InstantToLocalDateTimeConverter implements Converter<Instant, LocalDateTime> {

    @Override
    public LocalDateTime convert(MappingContext<Instant, LocalDateTime> context) {
        return context.getSource() == null ? null : DateTime.toDateTime(context.getSource());
    }
}
