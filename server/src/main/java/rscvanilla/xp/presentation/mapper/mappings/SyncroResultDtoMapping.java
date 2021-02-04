package rscvanilla.xp.presentation.mapper.mappings;

import org.modelmapper.ExpressionMap;
import org.modelmapper.builder.ConfigurableConditionExpression;
import rscvanilla.xp.domain.entities.SyncroResult;
import rscvanilla.xp.presentation.dto.SyncroResultDto;
import rscvanilla.xp.presentation.mapper.InstantToLocalDateTimeConverter;


public class SyncroResultDtoMapping implements ExpressionMap<SyncroResult, SyncroResultDto> {

    @Override
    public void configure(ConfigurableConditionExpression<SyncroResult, SyncroResultDto> mapping) {
        mapping.using(new InstantToLocalDateTimeConverter()).map(SyncroResult::getCreatedAt, SyncroResultDto::setInitialCheckDateTime);
        mapping.using(new InstantToLocalDateTimeConverter()).map(SyncroResult::getUpdatedAt, SyncroResultDto::setLastCheckDateTime);
    }
}
